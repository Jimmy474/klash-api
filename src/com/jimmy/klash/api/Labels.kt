package com.jimmy.klash.api

import io.ktor.resources.*

@Resource("/labels")
class Labels {
    @Resource("players")
    data class Players(val parent: Labels = Labels())

    @Resource("clans")
    data class Clans (val parent: Labels = Labels())
}