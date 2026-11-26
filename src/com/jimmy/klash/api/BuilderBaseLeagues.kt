package com.jimmy.klash.api

import io.ktor.resources.*

@Resource("/builderbaseleagues")
class BuilderBaseLeagues{
    @Resource("{id}") class Id(val id: String, val parent: BuilderBaseLeagues = BuilderBaseLeagues())
}

