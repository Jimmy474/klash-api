package com.jimmy.klash.api

import io.ktor.resources.*

@Resource("/warleagues")
class WarLeagues{
    @Resource("{id}")
    class Id(val id: String, val parent: WarLeagues = WarLeagues())
}