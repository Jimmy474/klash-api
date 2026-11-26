package com.jimmy.klash.api

import io.ktor.resources.*

@Resource("/leagues")
class Leagues{
    @Resource("{id}")
    class Id(val id: String, val parent: Leagues = Leagues()){

        @Resource("seasons")
        class Seasons(val parent: Id){
            @Resource("{seasonId}")
            class SeasonId(val seasonId: String, val parent: Seasons)
        }
    }
}