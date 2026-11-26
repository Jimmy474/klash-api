package com.jimmy.klash.api

import io.ktor.resources.*

@Resource("/leaguetiers")
class LeagueTiers{
    @Resource("{id}") class Id(val id: String, val parent: LeagueTiers = LeagueTiers())
}

