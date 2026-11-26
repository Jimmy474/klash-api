package com.jimmy.klash.api

import com.jimmy.klash.asTag
import io.ktor.resources.*

@Resource("/clans")
class Clans {

    @Resource("{tag}")
    class ClanTag(val tag: String, val parent: Clans = Clans()) {
        companion object {
            fun from(rawTag: String) = ClanTag(rawTag.asTag())
        }

        @Resource("currentwar")
        class CurrentWar(val parent: ClanTag) {
            constructor(tag: String) : this(from(tag))

            @Resource("leaguegroup")
            class LeagueGroup(val parent: CurrentWar) {
                constructor(tag: String) : this(CurrentWar(tag))
            }
        }

        @Resource("warlog")
        class WarLog(val parent: ClanTag) {
            constructor(tag: String) : this(from(tag))
        }

        @Resource("members")
        class Members(val parent: ClanTag) {
            constructor(tag: String) : this(from(tag))
        }

        @Resource("capitalraidseasons")
        class CapitalRaidSeasons(val parent: ClanTag) {
            constructor(tag: String) : this(from(tag))
        }
    }
}


