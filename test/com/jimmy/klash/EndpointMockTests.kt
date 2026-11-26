package com.jimmy.klash

import com.jimmy.klash.api.ClanQueryParams
import com.jimmy.klash.api.KlashAPI
import com.jimmy.klash.api.KlashApiException
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue

@Suppress("DEPRECATION")
class EndpointMockTests {
    private val headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
    private val paging = """{"items":[],"paging":{"cursors":{"before":"previous","after":"next"}}}"""

    @Test
    fun productionRequestsUseVersionedUrlAndToken() = runTest {
        val api = KlashAPI("secret", MockEngine { request ->
            assertEquals("api.clashofclans.com", request.url.host)
            assertEquals("/v1/locations", request.url.encodedPath)
            assertEquals("Bearer secret", request.headers[HttpHeaders.Authorization])
            respond(paging, HttpStatusCode.OK, headers)
        })
        api.getLocations()
    }

    @Test
    fun paginatedEndpointsDecodeResponses() = runTest {
        val api = KlashAPI(MockEngine { respond(paging, HttpStatusCode.OK, headers) })
        val results = listOf(
            api.getClans(), api.getClanMembers("ABC"), api.getClanWarLog("ABC"),
            api.getClanCapitalRaidSeasons("ABC"), api.getLeagueTiers(), api.getCapitalLeagues(),
            api.getBuilderBaseLeagues(), api.getWarLeagues(), api.getLeagueSeasons("1"),
            api.getLeagues(), api.getLocations(), api.getClansRankingForLocation("1"),
            api.getPlayersRankingsForLocation("1"), api.getPlayersBuilderBaseRankingsForLocation("1"),
            api.getClansBuilderBaseRankingsForLocation("1"), api.getClanCapitalsRankingForLocation("1"),
            api.getPlayerLabels(), api.getClanLabels()
        )
        results.forEach {
            assertTrue(it.items.isEmpty())
            assertEquals("next", it.paging.cursors.after)
            assertEquals("previous", it.paging.cursors.before)
        }
    }

    @Test
    fun arrayEndpointsDecodeResponses() = runTest {
        val api = KlashAPI(MockEngine { respond("""{"items":[]}""", HttpStatusCode.OK, headers) })
        assertTrue(api.getPlayerBattleLog("ABC").isEmpty())
        assertTrue(api.getPlayerLeagueHistory("ABC").isEmpty())
    }

    @Test
    fun clanPaginationAndFiltersAreSent() = runTest {
        val api = KlashAPI(MockEngine { request ->
            assertEquals("1", request.url.parameters["limit"])
            assertEquals("next+/=", request.url.parameters["after"])
            assertEquals("Test clan", request.url.parameters["name"])
            assertEquals("123,456", request.url.parameters["labelIds"])
            respond(paging, HttpStatusCode.OK, headers)
        })
        val result = api.getClans(ClanQueryParams(limit = 1, after = "next+/=", name = "Test clan", labelIds = listOf(123, 456)))
        assertEquals("previous", result.paging.cursors.before)
    }

    @Test
    fun beforeCursorAndValidation() = runTest {
        val api = KlashAPI(MockEngine { request ->
            assertEquals("1", request.url.parameters["limit"])
            assertEquals("prev+/=", request.url.parameters["before"])
            assertNull(request.url.parameters["after"])
            respond(paging, HttpStatusCode.OK, headers)
        })
        api.getClans(ClanQueryParams(limit = 1, before = "prev+/="))
        assertFailsWith<IllegalArgumentException> { ClanQueryParams(before = "a", after = "b") }
        assertFailsWith<IllegalArgumentException> { ClanQueryParams(limit = 0) }
    }

    @TestFactory
    fun allEndpointOperationsReportHttpErrors(): List<org.junit.jupiter.api.DynamicTest> {
        val api = KlashAPI(MockEngine { respond("""{"reason":"notFound","message":"missing"}""", HttpStatusCode.NotFound, headers) })
        val calls: List<Pair<String, suspend () -> Any?>> = listOf(
            "clans" to { api.getClans() }, "clan" to { api.getClan("ABC") },
            "members" to { api.getClanMembers("ABC") }, "currentWar" to { api.getClanCurrentWar("ABC") },
            "warLog" to { api.getClanWarLog("ABC") }, "warGroup" to { api.getClanCurrentWarLeagueGroup("ABC") },
            "leagueWar" to { api.getClanWarLeagueWar("ABC") }, "raids" to { api.getClanCapitalRaidSeasons("ABC") },
            "player" to { api.getPlayer("ABC") }, "battleLog" to { api.getPlayerBattleLog("ABC") },
            "leagueHistory" to { api.getPlayerLeagueHistory("ABC") }, "verify" to { api.verifyPlayerToken("ABC", "token") },
            "tiers" to { api.getLeagueTiers() }, "tier" to { api.getLeagueTier("1") },
            "capitalLeagues" to { api.getCapitalLeagues() }, "capitalLeague" to { api.getCapitalLeague("1") },
            "builderLeagues" to { api.getBuilderBaseLeagues() }, "builderLeague" to { api.getBuilderBaseLeague("1") },
            "warLeagues" to { api.getWarLeagues() }, "warLeague" to { api.getWarLeague("1") },
            "seasons" to { api.getLeagueSeasons("1") }, "season" to { api.getLeagueSeason("1", "2024-01") },
            "leagueGroup" to { api.getLeagueGroupSeason("ABC", "2024-01", "ABC") },
            "leagues" to { api.getLeagues() }, "league" to { api.getLeague("1") },
            "locations" to { api.getLocations() }, "location" to { api.getLocation("1") },
            "clanRanking" to { api.getClansRankingForLocation("1") }, "playerRanking" to { api.getPlayersRankingsForLocation("1") },
            "playerBuilderRanking" to { api.getPlayersBuilderBaseRankingsForLocation("1") },
            "clanBuilderRanking" to { api.getClansBuilderBaseRankingsForLocation("1") },
            "capitalRanking" to { api.getClanCapitalsRankingForLocation("1") },
            "goldPass" to { api.getCurrentGoldPassSeason() }, "playerLabels" to { api.getPlayerLabels() },
            "clanLabels" to { api.getClanLabels() }
        )
        assertEquals(35, calls.size)
        return calls.map { (name, call) ->
            dynamicTest(name) {
                runTest {
                    val error = assertFailsWith<KlashApiException>(name) { call() }
                    assertEquals(HttpStatusCode.NotFound, error.statusCode, name)
                    assertEquals("notFound", error.response.reason, name)
                    assertEquals("missing", error.message, name)
                }
            }
        }
    }

    @Test
    fun orNullHandlesHttpErrorsButNotTransportFailures() = runTest {
        val api = KlashAPI(MockEngine { respond("""{"reason":"notFound","message":"missing"}""", HttpStatusCode.NotFound, headers) })
        assertNull(api.getClanOrNull("ABC"))
        assertNull(api.verifyPlayerTokenOrNull("ABC", "token"))
        val broken = KlashAPI(MockEngine { throw IllegalStateException("transport") })
        assertFailsWith<IllegalStateException> { broken.getClanOrNull("ABC") }
    }

    @Test
    fun clanListOrNullHandlesHttpErrors() = runTest {
        val api = KlashAPI(MockEngine { respond("""{"reason":"notFound","message":"missing"}""", HttpStatusCode.NotFound, headers) })
        assertNull(api.getClansOrNull())
    }
}
