package com.jimmy.klash.api

import io.ktor.resources.*

@Resource("/leaguegroup/{tag}/{id}")
class LeagueGroups(val tag: String, val id: String)