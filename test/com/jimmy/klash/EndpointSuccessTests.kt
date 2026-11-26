package com.jimmy.klash

import com.jimmy.klash.api.ClanQueryParams
import com.jimmy.klash.api.KlashAPI
import com.jimmy.klash.api.QueryParams
import com.jimmy.klash.model.ClanWarLeagueWar
import com.jimmy.klash.model.ClanWarResponse
import com.jimmy.klash.model.VerifyTokenStatus
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.http.content.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import kotlin.test.assertEquals
import kotlin.test.assertIs

@Suppress("DEPRECATION")
class EndpointSuccessTests {
    private val headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
    private val badge = """{"small":"https://example.com/s.png","medium":"https://example.com/m.png","large":"https://example.com/l.png"}"""
    private val tier = """{"id":1,"name":"Bronze","iconUrls":{"small":"https://example.com/s.png","large":"https://example.com/l.png"}}"""
    private val league = """{"id":1,"name":"Bronze","iconUrls":{"tiny":"https://example.com/t.png","small":"https://example.com/s.png"}}"""
    private val simpleLeague = """{"id":1,"name":"Bronze"}"""
    private val label = """{"id":1,"name":"Friendly","iconUrls":{"small":"https://example.com/s.png","medium":"https://example.com/m.png"}}"""
    private val location = """{"id":1,"name":"International","isCountry":false}"""
    private val member = """{"tag":"#ABC","name":"Alice","role":"leader","expLevel":100,"league":$league,"leagueTier":$tier,"trophies":2500,"builderBaseLeague":$simpleLeague,"builderBaseTrophies":2100,"clanRank":1,"previousClanRank":2,"donations":20,"donationsReceived":10,"townHallLevel":14}"""
    private val search = """{"tag":"#ABC","name":"Test clan","type":"open","badgeUrls":$badge,"capitalLeague":$simpleLeague,"clanBuilderBasePoints":1000,"clanCapitalPoints":500,"clanLevel":10,"clanPoints":2000,"isFamilyFriendly":false,"isWarLogPublic":true,"labels":[$label],"members":1,"requiredBuilderBaseTrophies":0,"requiredTownhallLevel":10,"requiredTrophies":0,"warFrequency":"always","warLeague":$simpleLeague,"warWinStreak":2,"warWins":30}"""
    private val clan = search.dropLast(1) + """, "description":"Welcome","clanCapital":{"clanGoldSinkTotal":1234,"capitalHallLevel":5},"memberList":[$member]}"""
    private val player = """{"tag":"#ABC","name":"Alice","townHallLevel":14,"expLevel":100,"trophies":2500,"bestTrophies":2600,"warStars":50,"attackWins":10,"defenseWins":2,"builderBaseTrophies":2100,"bestBuilderBaseTrophies":2200,"clanCapitalContributions":100,"donations":20,"donationsReceived":10,"previousLeagueSeasonId":202401,"leagueTier":$tier,"achievements":[],"heroEquipment":[],"heroes":[],"labels":[$label],"spells":[],"troops":[]}"""
    private val warClan = """{"tag":"#ABC","name":"Test clan","badgeUrls":$badge,"clanLevel":10,"attacks":3,"destructionPercentage":80.0,"members":[],"stars":6}"""
    private val compact = "20241231T120000.000+0000"
    private val war = """{"state":"inWar","teamSize":5,"clan":$warClan,"opponent":$warClan,"preparationStartTime":"$compact","startTime":"$compact","endTime":"$compact"}"""
    private val warLogClan = """{"tag":"#ABC","name":"Test clan","badgeUrls":$badge,"clanLevel":10,"attacks":3,"destructionPercentage":80.0,"stars":6,"expEarned":50}"""
    private val warLogOpponent = """{"tag":"#DEF","name":"Opponent","badgeUrls":$badge,"clanLevel":10,"destructionPercentage":50.0,"stars":3}"""
    private val warLog = """{"attacksPerMember":2,"battleModifier":"none","clan":$warLogClan,"opponent":$warLogOpponent,"endTime":"$compact","result":"win","teamSize":5}"""
    private val cwlGroup = """{"state":"inWar","season":"2024-12","clans":[{"tag":"#ABC","name":"Test clan","clanLevel":10,"members":[{"tag":"#ABC","name":"Alice","townHallLevel":14}]}],"rounds":[{"warTags":["#WAR"]}]}"""
    private val raid = """{"state":"ended","startTime":"$compact","endTime":"$compact","capitalTotalLoot":1000,"raidsCompleted":1,"totalAttacks":3,"enemyDistrictsDestroyed":2,"offensiveReward":100,"defensiveReward":50,"attackLog":[],"defenseLog":[]}"""
    private val battle = """{"attack":true,"availableLoot":[],"battleTime":120,"battleTimestamp":"$compact","battleType":"ranked","destructionPercentage":90,"extraLootedResources":[],"lootedResources":[],"opponentName":"Bob","opponentPlayerTag":"#DEF","opponentTownHallLevel":14,"stars":3}"""
    private val history = """{"attackLosses":1,"attackStars":20,"attackWins":8,"defenseLosses":2,"defenseStars":5,"defenseWins":1,"leagueSeasonId":202401,"leagueTierId":1,"leagueTrophies":2000,"maxBattles":10,"placement":1}"""
    private val leagueGroup = """{"attackLogs":[{"creationTime":"2024-12-31T12:00:00Z","destructionPercentage":90,"opponentName":"Bob","opponentPlayerTag":"#DEF","stars":3,"trophies":20}],"defenseLogs":[],"members":[{"attackLoseCount":1,"attackWinCount":8,"defenseLoseCount":2,"defenseWinCount":1,"leagueTrophies":2000,"playerName":"Alice","playerTag":"#ABC"}]}"""
    private val clanRank = """{"tag":"#ABC","name":"Test clan","badgeUrls":$badge,"clanLevel":10,"clanPoints":2000,"clanBuilderBasePoints":1000,"clanCapitalPoints":500,"members":1,"rank":1,"previousRank":2}"""
    private val playerRank = """{"tag":"#ABC","name":"Alice","rank":1,"previousRank":2,"expLevel":100,"attackWins":10,"defenseWins":2,"league":$league,"leagueTier":$tier,"trophies":2500,"builderBaseLeague":$simpleLeague,"builderBaseTrophies":2100}"""
    private fun page(item: String) = """{"items":[$item],"paging":{"cursors":{"before":"previous","after":"next"}}}"""
    private val params = QueryParams(limit = 1, after = "next+/=")
    private val query = mapOf("limit" to listOf("1"), "after" to listOf("next+/="))

    private data class Case(val name: String, val path: String, val response: String, val query: Map<String, List<String>> = emptyMap(), val method: HttpMethod = HttpMethod.Get, val call: suspend (KlashAPI) -> Unit)

    @TestFactory
    fun successfulEndpoints() : List<org.junit.jupiter.api.DynamicTest> {
        val cases = listOf(
            Case("clans", "/clans", page(search), query) { assertEquals("Test clan", it.getClans(ClanQueryParams(limit = 1, after = "next+/=")).items.single().name) },
            Case("clan", "/clans/%23ABC", clan) { assertEquals("Welcome", it.getClan("ABC").description) },
            Case("members", "/clans/%23ABC/members", page(member), query) { assertEquals("Alice", it.getClanMembers("ABC", params).items.single().name) },
            Case("currentWar", "/clans/%23ABC/currentwar", war) { assertEquals("Test clan", assertIs<ClanWarResponse.ClanWar>(it.getClanCurrentWar("ABC")).clan.name) },
            Case("warLog", "/clans/%23ABC/warlog", page(warLog), query) { assertEquals(5, it.getClanWarLog("ABC", params).items.single().teamSize) },
            Case("warGroup", "/clans/%23ABC/currentwar/leaguegroup", cwlGroup) { assertEquals("#WAR", it.getClanCurrentWarLeagueGroup("ABC").rounds!!.single().warTags!!.single()) },
            Case("leagueWar", "/clanwarleagues/wars/%23ABC", war) { assertEquals(5, assertIs<ClanWarLeagueWar>(it.getClanWarLeagueWar("ABC")).teamSize) },
            Case("raids", "/clans/%23ABC/capitalraidseasons", page(raid), query) { assertEquals(1000, it.getClanCapitalRaidSeasons("ABC", params).items.single().capitalTotalLoot) },
            Case("player", "/players/%23ABC", player) { assertEquals(2500, it.getPlayer("ABC").trophies) },
            Case("battleLog", "/players/%23ABC/battlelog", """{"items":[$battle]}""") { assertEquals("Bob", it.getPlayerBattleLog("ABC").single().opponentName) },
            Case("leagueHistory", "/players/%23ABC/leaguehistory", """{"items":[$history]}""") { assertEquals(202401L, it.getPlayerLeagueHistory("ABC").single().leagueSeasonId) },
            Case("verify", "/players/%23ABC/verifytoken", """{"tag":"#ABC","token":"secret","status":"ok"}""", method = HttpMethod.Post) { assertEquals(VerifyTokenStatus.Ok, it.verifyPlayerToken("ABC", "secret").status) },
            Case("tiers", "/leaguetiers", page(tier), query) { assertEquals("Bronze", it.getLeagueTiers(params).items.single().name) },
            Case("tier", "/leaguetiers/1", tier) { assertEquals(1, it.getLeagueTier("1").id) },
            Case("capitalLeagues", "/capitalleagues", page(simpleLeague), query) { assertEquals(1, it.getCapitalLeagues(params).items.single().id) },
            Case("capitalLeague", "/capitalleagues/1", simpleLeague) { assertEquals("Bronze", it.getCapitalLeague("1").name) },
            Case("builderLeagues", "/builderbaseleagues", page(simpleLeague), query) { assertEquals(1, it.getBuilderBaseLeagues(params).items.single().id) },
            Case("builderLeague", "/builderbaseleagues/1", simpleLeague) { assertEquals("Bronze", it.getBuilderBaseLeague("1").name) },
            Case("warLeagues", "/warleagues", page(simpleLeague), query) { assertEquals(1, it.getWarLeagues(params).items.single().id) },
            Case("warLeague", "/warleagues/1", simpleLeague) { assertEquals("Bronze", it.getWarLeague("1").name) },
            Case("seasons", "/leagues/1/seasons", page("""{"id":"2024-01"}"""), query) { assertEquals("2024-01", it.getLeagueSeasons("1", params).items.single().id) },
            Case("season", "/leagues/1/seasons/2024-01", page(playerRank), query) { val result = it.getLeagueSeason("1", "2024-01", params); assertEquals("Alice", result.items.single().name); assertEquals("next", result.paging.cursors.after) },
            Case("leagueGroup", "/leaguegroup/ABC/2024-01", leagueGroup, mapOf("playerTag" to listOf("#ABC"))) { val result = it.getLeagueGroupSeason("ABC", "2024-01", "#ABC"); assertEquals("Alice", result.members.single().playerName); assertEquals(2024, result.attackLogs.single().creationTime.year) },
            Case("leagues", "/leagues", page(league), query) { assertEquals(1, it.getLeagues(params).items.single().id) },
            Case("league", "/leagues/1", league) { assertEquals("Bronze", it.getLeague("1").name) },
            Case("locations", "/locations", page(location), query) { assertEquals(1, it.getLocations(params).items.single().id) },
            Case("location", "/locations/1", location) { assertEquals("International", it.getLocation("1").name) },
            Case("clanRanking", "/locations/1/rankings/clans", page(clanRank), query) { assertEquals(2000, it.getClansRankingForLocation("1", params).items.single().clanPoints) },
            Case("playerRanking", "/locations/1/rankings/players", page(playerRank), query) { assertEquals(2500, it.getPlayersRankingsForLocation("1", params).items.single().trophies) },
            Case("playerBuilderRanking", "/locations/1/rankings/players-builder-base", page(playerRank), query) { assertEquals(2100, it.getPlayersBuilderBaseRankingsForLocation("1", params).items.single().builderBaseTrophies) },
            Case("clanBuilderRanking", "/locations/1/rankings/clans-builder-base", page(clanRank), query) { assertEquals(1000, it.getClansBuilderBaseRankingsForLocation("1", params).items.single().clanBuilderBasePoints) },
            Case("capitalRanking", "/locations/1/rankings/capitals", page(clanRank), query) { assertEquals(500, it.getClanCapitalsRankingForLocation("1", params).items.single().clanCapitalPoints) },
            Case("goldPass", "/goldpass/seasons/current", """{"startTime":"$compact","endTime":"$compact"}""") { assertEquals(2024, it.getCurrentGoldPassSeason().startTime.year) },
            Case("playerLabels", "/labels/players", page(label), query) { assertEquals("Friendly", it.getPlayerLabels(params).items.single().name) },
            Case("clanLabels", "/labels/clans", page(label), query) { assertEquals("Friendly", it.getClanLabels(params).items.single().name) }
        )
        assertEquals(35, cases.size)
        return cases.map { case -> dynamicTest(case.name) { runTest {
            var requests = 0
            val api = KlashAPI(MockEngine { request ->
                requests++
                assertEquals(case.method, request.method)
                assertEquals(case.path, request.url.encodedPath)
                assertEquals(case.query, request.url.parameters.entries().associate { it.key to it.value })
                if (case.method == HttpMethod.Post) {
                    assertEquals(ContentType.Application.Json, request.body.contentType?.withoutParameters())
                    val body = (request.body as OutgoingContent.ByteArrayContent).bytes().decodeToString()
                    assertEquals(Json.parseToJsonElement("""{"token":"secret"}"""), Json.parseToJsonElement(body))
                }
                respond(case.response, HttpStatusCode.OK, headers)
            })
            case.call(api)
            assertEquals(1, requests)
        } } }
    }
}
