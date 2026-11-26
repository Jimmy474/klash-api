package com.jimmy.klash.api

import io.ktor.resources.*

@Resource("/goldpass")
class GoldPass{
    @Resource("/seasons")
    class Seasons(val parent: GoldPass = GoldPass()){
        @Resource("/current") class Current(val parent: Seasons = Seasons())
    }
}