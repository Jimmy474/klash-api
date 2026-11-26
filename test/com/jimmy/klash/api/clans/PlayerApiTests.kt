package com.jimmy.klash.api.clans

import com.jimmy.klash.api.KlashAPI
import com.jimmy.klash.api.KlashApiException
import com.jimmy.klash.model.VerifyTokenStatus
import io.ktor.http.*
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PlayerApiTests {

    val api = KlashAPI()

    @Test
    fun testPlayerAPI() = runTest {
        assertNotNull(api.getPlayer("2pp"))
        assertEquals(HttpStatusCode.NotFound,assertFailsWith<KlashApiException>{
            api.getPlayer("2ppp")
        }.statusCode)
    }

    @Test
    fun testBattleLogAPI() = runTest {
        assert(api.getPlayerBattleLog("2pp").isNotEmpty())
    }

    @Test
    fun testLeagueHistoryAPI() = runTest {
        assert(api.getPlayerLeagueHistory("2pp").isNotEmpty())
    }

    @Test
    fun testVerifyTokenAPI() = runTest {
        assertEquals(VerifyTokenStatus.Ok,api.verifyPlayerToken("2pp","TOKEN").status)
        assertEquals(VerifyTokenStatus.Invalid,api.verifyPlayerToken("2pp","").status)
        assertEquals(HttpStatusCode.NotFound,assertFailsWith<KlashApiException>{
            api.verifyPlayerToken("2ppp","")
        }.statusCode)
    }

}