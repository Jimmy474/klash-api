package com.jimmy.klash.api

import com.jimmy.klash.asTag
import io.ktor.resources.*

@Resource("/clanwarleagues/wars/{tag}")
class ClanWarLeagues(val tag: String) {
    companion object {
        fun from(rawTag: String) = ClanWarLeagues(rawTag.asTag())
    }
}
