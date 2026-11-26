package com.jimmy.klash.api

import com.jimmy.klash.asTag
import io.ktor.resources.*

@Resource("/players/{playerTag}")
class Players(val playerTag: String) {

    companion object {
        fun from(tag: String): Players {
            return Players(tag.asTag())
        }
    }

    @Resource("battlelog")
    class BattleLog(val parent: Players){
        constructor(tag: String): this(Players.from(tag))
    }

    @Resource("verifytoken")
    class VerifyToken(val parent: Players){
        constructor(tag: String): this(Players.from(tag))
    }

    @Resource("leaguehistory")
    class LeagueHistory(val parent: Players){
        constructor(tag: String): this(Players.from(tag))
    }
}

