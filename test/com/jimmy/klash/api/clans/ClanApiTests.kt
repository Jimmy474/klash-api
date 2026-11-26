package com.jimmy.klash.api.clans

import com.jimmy.klash.api.ClanQueryParams
import com.jimmy.klash.api.KlashAPI
import com.jimmy.klash.api.KlashApiException
import com.jimmy.klash.model.*
import io.ktor.http.*
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs

class ClanApiTests {
    val api = KlashAPI()

    @Test
    fun testClansAPI() = runTest{
        api.getClans().also {
            assert(it.items.isNotEmpty())
        }
        api.getClans(ClanQueryParams(limit = 1, minClanLevel = 1)).also {
            assert(it.items.isNotEmpty())
            assert(it.items.size == 1)
        }
        api.getClans(ClanQueryParams(before = "1", minClanLevel = 1)).also {
            assert(it.items.isNotEmpty())
        }
        api.getClans(ClanQueryParams(after = "1", minClanLevel = 1)).also {
            assert(it.items.isNotEmpty())
        }
        api.getClans(ClanQueryParams(name = "test")).also {
            assert(it.items.isNotEmpty())
            assert(it.items.all { it.name.contains("test") })
        }
        api.getClans(ClanQueryParams(warFrequency = WarFrequency.Always)).also {
            assert(it.items.isNotEmpty())
            assert(it.items.all { it.warFrequency == WarFrequency.Always })
        }
        api.getClans(ClanQueryParams(locationId = 32000006)).also {
            assert(it.items.size == 1)
            assert(it.items.single().location?.id == 32000006)
        }
        api.getClans(ClanQueryParams(minMembers = 48)).also {
            assert(it.items.size == 1)
            assert(it.items.single().members >= 48)
        }
        api.getClans(ClanQueryParams(maxMembers = 31)).also {
            assert(it.items.size == 1)
            assert(it.items.single().members <= 31)
        }
        api.getClans(ClanQueryParams(minClanPoints = 71600)).also {
            assert(it.items.size == 1)
            assert(it.items.single().clanPoints >= 71600)
        }
        api.getClans(ClanQueryParams(minClanLevel = 15)).also {
            assert(it.items.size == 1)
            assert(it.items.single().clanLevel >= 15)

        }
        api.getClans(ClanQueryParams(labelIds = listOf(56000002))).also {
            assert(it.items.size == 1)
            assert(it.items.single().labels.any{ it.id == 56000002 })
        }
    }

    @Test
    fun testClanAPI() = runTest{
        api.getClan("2pp").also{
            assert(!it.clanCapital.districts.isNullOrEmpty())
            assert(it.isWarLogPublic)
            assert(it.warLosses != null)
            assert(it.warTies != null)
        }
        api.getClan("2ppp").also{
            assert(it.clanCapital.districts.isNullOrEmpty())
            assert(!it.isWarLogPublic)
            assert(it.warLosses == null)
            assert(it.warTies == null)
        }
    }

    @Test
    fun testClanMembersAPI() = runTest{
        api.getClanMembers("2pp").also{
            assert(it.items.isNotEmpty())
            assert(it.items.singleOrNull{ it.role == Role.Leader } != null)
        }
    }

    @Test
    fun testCurrentWarAPI() = runTest{
        api.getClanCurrentWar("2pp").also{ assertIs<ClanWarResponse.ClanWar>(it); assert(it.state == ClanWarState.InWar) }
        api.getClanCurrentWar("2ppp").also{ assertIs<ClanWarResponse.ClanWar>(it); assert(it.state == ClanWarState.Preparation) }
        api.getClanCurrentWar("2pppp").also{ assertIs<ClanWarResponse.ClanWar>(it); assert(it.state == ClanWarState.WarEnded) }
        api.getClanCurrentWar("2ppppp").also{ assertIs<ClanWarResponse.NotInWar>(it); assert(it.state == ClanWarState.NotInWar) }
        assertEquals(HttpStatusCode.Forbidden,assertFailsWith<KlashApiException>{
            api.getClanCurrentWar("2pppppp")
        }.statusCode)
    }

    @Test
    fun testWarLogAPI() = runTest{
        api.getClanWarLog("2pp").also{ assert(it.items.isNotEmpty()) }
        assertEquals(HttpStatusCode.Forbidden,assertFailsWith<KlashApiException>{
            api.getClanWarLog("2ppp")
        }.statusCode)
    }

    @Test
    fun testCWLGroupAPI() = runTest {
        api.getClanCurrentWarLeagueGroup("2pp").also{ assert(it.state == ClanWarState.InWar) }
        api.getClanCurrentWarLeagueGroup("2ppp").also{ assert(it.state == ClanWarState.Ended) }
        assertEquals(HttpStatusCode.NotFound,assertFailsWith<KlashApiException>{
            api.getClanCurrentWarLeagueGroup("2pppp")
        }.statusCode)
    }

    @Test
    fun testCWLWarAPI() = runTest{
        api.getClanWarLeagueWar("2pp").also{ assert(it.state == ClanWarState.InWar) }
        api.getClanWarLeagueWar("2ppp").also{ assert(it.state == ClanWarState.Preparation) }
        api.getClanWarLeagueWar("2pppp").also{ assert(it.state == ClanWarState.WarEnded) }
    }

    @Test
    fun testRaidSeasonsAPI() = runTest {
        api.getClanCapitalRaidSeasons("2pp").also{
            assert(it.items.isNotEmpty())
            it.items.filter{ it.state == ClanCapitalRaidSeasonState.Ongoing }.forEach {
                assert(!it.members.isNullOrEmpty())
            }
        }
    }
}