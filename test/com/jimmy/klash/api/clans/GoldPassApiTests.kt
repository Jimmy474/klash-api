package com.jimmy.klash.api.clans

import com.jimmy.klash.api.KlashAPI
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull

class GoldPassApiTests {

    val api = KlashAPI()

    @Test
    fun testGoldPassAPI() = runTest {
        assertNotNull(api.getCurrentGoldPassSeasonOrNull())
    }

}