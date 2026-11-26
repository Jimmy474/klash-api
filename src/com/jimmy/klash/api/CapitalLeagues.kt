package com.jimmy.klash.api

import io.ktor.resources.*

@Resource("/capitalleagues")
class CapitalLeagues{
    @Resource("{id}") class Id(val id: String, val parent: CapitalLeagues = CapitalLeagues())
}

