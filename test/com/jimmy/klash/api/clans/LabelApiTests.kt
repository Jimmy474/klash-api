package com.jimmy.klash.api.clans

import com.jimmy.klash.api.KlashAPI
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class LabelApiTests {

    val api = KlashAPI()

    @Test
    fun testClanLabelsAPI() = runTest {
        api.getClanLabels().also{ assert(it.items.isNotEmpty()) }
    }

    @Test
    fun testPlayerLabelsAPI() = runTest {
        api.getPlayerLabels().also{ assert(it.items.isNotEmpty()) }
    }

}