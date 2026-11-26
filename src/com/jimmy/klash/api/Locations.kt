package com.jimmy.klash.api

import io.ktor.resources.*

@Resource("/locations")
class Locations{
    @Resource("{id}")
    class Id(val id: String, val parent: Locations = Locations()){
        @Resource("rankings")
        class Rankings(val parent: Id){

            @Resource("clans")
            class Clans(val parent: Rankings){
                constructor(id: String): this(Rankings(Id(id)))
            }
            @Resource("players")
            class Players(val parent: Rankings){
                constructor(id: String): this(Rankings(Id(id)))
            }
            @Resource("players-builder-base")
            class PlayersBuilderBase(val parent: Rankings){
                constructor(id: String): this(Rankings(Id(id)))
            }
            @Resource("clans-builder-base")
            class ClansBuilderBase(val parent: Rankings){
                constructor(id: String): this(Rankings(Id(id)))
            }
            @Resource("capitals")
            class Capitals(val parent: Rankings){
                constructor(id: String): this(Rankings(Id(id)))
            }
        }
    }
}