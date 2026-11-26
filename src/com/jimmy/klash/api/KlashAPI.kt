package com.jimmy.klash.api

import com.jimmy.klash.addQueryParams
import com.jimmy.klash.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.engine.java.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory

class KlashAPI private constructor(
    private val token: String,
    private val engine: HttpClientEngine,
    private val logger: org.slf4j.Logger,
    private val testing: Boolean
) {

    constructor(
        token: String,
        engine: HttpClientEngine = Java.create(),
        logger: org.slf4j.Logger = LoggerFactory.getLogger(KlashAPI::class.java),
    ) : this(token, engine, logger, false)

    internal constructor(
        engine: HttpClientEngine = Java.create(),
        logger: org.slf4j.Logger = LoggerFactory.getLogger(KlashAPI::class.java),
    ) : this("", engine, logger, true)

    private val client: HttpClient = HttpClient(engine) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO

            sanitizeHeader {
                it == HttpHeaders.Authorization
            }
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }

        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(token, null)
                }
                sendWithoutRequest { true }
            }
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 15000
            connectTimeoutMillis = 10000
            socketTimeoutMillis = 15000
        }

        install(Resources)

        install(HttpRequestRetry) {
            retryIf(maxRetries = 2) { request, response ->
                request.method == HttpMethod.Get && response.status.value in 500..599
            }

            exponentialDelay()
        }

        expectSuccess = false

        defaultRequest {
            host = if (testing) TESTING_URL else "api.clashofclans.com"
            url {
                protocol = URLProtocol.HTTPS
                if (!testing) encodedPath = "/v1/"
            }
            userAgent("KlashAPI/1.0.0")
            accept(ContentType.Application.Json)
        }
    }

    companion object{
        const val PRODUCTION_URL = "api.clashofclans.com/v1"
        const val TESTING_URL = "mock.clashk.ing"
        private const val LEAGUE_DEPRECATION_MESSAGE = "Supercell replaced Home Village trophy leagues with ranked league tiers `/leaguetiers/{leagueTierId}`, though this endpoint still returns HTTP 200 on live servers."
    }

    //region Clan Endpoints

    suspend fun getClans(queryParams: ClanQueryParams = ClanQueryParams(minClanLevel = 1)): PagingList<ClanSearchItem> = client.get(Clans()){
        addQueryParams(queryParams)
    }.bodyOrThrow<PagingList<ClanSearchItem>>()

    suspend fun getClansOrNull(queryParams: ClanQueryParams = ClanQueryParams(minClanLevel = 1)): PagingList<ClanSearchItem>? = getOrNull { getClans(queryParams) }

    suspend fun getClan(clanTag: String): Clan = client.get(Clans.ClanTag.from(clanTag)).bodyOrThrow()

    suspend fun getClanOrNull(clanTag: String): Clan? = getOrNull { getClan(clanTag) }

    suspend fun getClanMembers(clanTag: String, queryParams: QueryParams = QueryParams()): PagingList<ClanMember> = client.get(Clans.ClanTag.Members(clanTag)){
        addQueryParams(queryParams)
    }.bodyOrThrow<PagingList<ClanMember>>()

    suspend fun getClanMembersOrNull(clanTag: String, queryParams: QueryParams = QueryParams()): PagingList<ClanMember>? = getOrNull { getClanMembers(clanTag, queryParams) }

    suspend fun getClanCurrentWar(clanTag: String): ClanWarResponse = client.get(Clans.ClanTag.CurrentWar(clanTag)).bodyOrThrow()

    suspend fun getClanCurrentWarOrNull(clanTag: String): ClanWarResponse? = getOrNull { getClanCurrentWar(clanTag) }

    suspend fun getClanWarLog(clanTag: String, queryParams: QueryParams = QueryParams()): PagingList<ClanWarLogEntry> = client.get(Clans.ClanTag.WarLog(clanTag)){
        addQueryParams(queryParams)
    }.bodyOrThrow<PagingList<ClanWarLogEntry>>()

    suspend fun getClanWarLogOrNull(warTag: String, queryParams: QueryParams = QueryParams()): PagingList<ClanWarLogEntry>? = getOrNull { getClanWarLog(warTag, queryParams) }

    suspend fun getClanCurrentWarLeagueGroup(clanTag: String): ClanWarLeagueGroup = client.get(Clans.ClanTag.CurrentWar.LeagueGroup(clanTag)).bodyOrThrow<ClanWarLeagueGroup>()

    suspend fun getClanCurrentWarLeagueGroupOrNull(clanTag: String): ClanWarLeagueGroup? = getOrNull { getClanCurrentWarLeagueGroup(clanTag) }

    suspend fun getClanWarLeagueWar(warTag: String): ClanWarLeagueWar = client.get(ClanWarLeagues.from(warTag)).bodyOrThrow<ClanWarLeagueWar>()

    suspend fun getClanWarLeagueWarOrNull(warTag: String): ClanWarLeagueWar? = getOrNull { getClanWarLeagueWar(warTag) }

    suspend fun getClanCapitalRaidSeasons(clanTag: String, queryParams: QueryParams = QueryParams()): PagingList<ClanCapitalRaidSeason> = client.get(Clans.ClanTag.CapitalRaidSeasons(clanTag)) {
        addQueryParams(queryParams)
    }.bodyOrThrow<PagingList<ClanCapitalRaidSeason>>()

    suspend fun getClanCapitalRaidSeasonsOrNull(clanTag: String, queryParams: QueryParams = QueryParams()): PagingList<ClanCapitalRaidSeason>? = getOrNull { getClanCapitalRaidSeasons(clanTag, queryParams) }

    //endregion

    //region Player Endpoints
    suspend fun getPlayer(playerTag: String): Player = client.get(Players.from(playerTag)).bodyOrThrow()

    suspend fun getPlayerOrNull(playerTag: String): Player? = getOrNull { getPlayer(playerTag) }

    suspend fun getPlayerBattleLog(playerTag: String): List<BattleLogEntry>  = client.get(Players.BattleLog(playerTag)).bodyOrThrow<ItemsList<BattleLogEntry>>().items

    suspend fun getPlayerBattleLogOrNull(playerTag: String): List<BattleLogEntry>? = getOrNull { getPlayerBattleLog(playerTag) }

    suspend fun getPlayerLeagueHistory(playerTag: String): List<LeagueSeasonResult>  = client.get(Players.LeagueHistory(playerTag)).bodyOrThrow<ItemsList<LeagueSeasonResult>>().items

    suspend fun getPlayerLeagueHistoryOrNull(playerTag: String): List<LeagueSeasonResult>? = getOrNull { getPlayerLeagueHistory(playerTag) }

    suspend fun verifyPlayerToken(playerTag: String, token: String): VerifyTokenResponse = client.post(Players.VerifyToken(playerTag)) {
        contentType(ContentType.Application.Json)
        setBody(VerifyTokenRequest(token))
    }.bodyOrThrow()

    suspend fun verifyPlayerTokenOrNull(playerTag: String, token: String): VerifyTokenResponse? = getOrNull { verifyPlayerToken(playerTag, token) }
    //endregion

    //region League Endpoints

    suspend fun getLeagueTiers(queryParams: QueryParams = QueryParams()): PagingList<LeagueTier> = client.get(LeagueTiers()){
        addQueryParams(queryParams)
    }.bodyOrThrow<PagingList<LeagueTier>>()

    suspend fun getLeagueTiersOrNull(queryParams: QueryParams = QueryParams()): PagingList<LeagueTier>? = getOrNull { getLeagueTiers(queryParams) }

    suspend fun getLeagueTier(id: String): LeagueTier = client.get(LeagueTiers.Id(id)).bodyOrThrow<LeagueTier>()

    suspend fun getLeagueTierOrNull(id: String): LeagueTier? = getOrNull { getLeagueTier(id) }

    suspend fun getCapitalLeagues(queryParams: QueryParams = QueryParams()): PagingList<CapitalLeague> = client.get(CapitalLeagues()){
        addQueryParams(queryParams)
    }.bodyOrThrow<PagingList<CapitalLeague>>()

    suspend fun getCapitalLeaguesOrNull(queryParams: QueryParams = QueryParams()): PagingList<CapitalLeague>? = getOrNull { getCapitalLeagues(queryParams) }

    suspend fun getCapitalLeague(id: String): CapitalLeague = client.get(CapitalLeagues.Id(id)).bodyOrThrow<CapitalLeague>()

    suspend fun getCapitalLeagueOrNull(id: String): CapitalLeague? = getOrNull { getCapitalLeague(id) }

    suspend fun getBuilderBaseLeagues(queryParams: QueryParams = QueryParams()): PagingList<BuilderBaseLeague> = client.get(BuilderBaseLeagues()){
        addQueryParams(queryParams)
    }.bodyOrThrow<PagingList<BuilderBaseLeague>>()

    suspend fun getBuilderBaseLeaguesOrNull(queryParams: QueryParams = QueryParams()): PagingList<BuilderBaseLeague>? = getOrNull { getBuilderBaseLeagues(queryParams) }

    suspend fun getBuilderBaseLeague(id: String): BuilderBaseLeague = client.get(BuilderBaseLeagues.Id(id)).bodyOrThrow<BuilderBaseLeague>()

    suspend fun getBuilderBaseLeagueOrNull(id: String): BuilderBaseLeague? = getOrNull { getBuilderBaseLeague(id) }

    suspend fun getWarLeagues(queryParams: QueryParams = QueryParams()): PagingList<WarLeague> = client.get(WarLeagues()){
        addQueryParams(queryParams)
    }.bodyOrThrow<PagingList<WarLeague>>()

    suspend fun getWarLeaguesOrNull(queryParams: QueryParams = QueryParams()): PagingList<WarLeague>? = getOrNull { getWarLeagues(queryParams) }

    suspend fun getWarLeague(id: String): WarLeague = client.get(WarLeagues.Id(id)).bodyOrThrow<WarLeague>()

    suspend fun getWarLeagueOrNull(id: String): WarLeague? = getOrNull { getWarLeague(id) }

    suspend fun getLeagueSeasons(id: String, queryParams: QueryParams = QueryParams()): PagingList<LeagueSeason> = client.get(Leagues.Id.Seasons(Leagues.Id(id))){
        addQueryParams(queryParams)
    }.bodyOrThrow<PagingList<LeagueSeason>>()

    suspend fun getLeagueSeasonsOrNull(id: String, queryParams: QueryParams = QueryParams()): PagingList<LeagueSeason>? = getOrNull { getLeagueSeasons(id, queryParams) }

    suspend fun getLeagueSeason(id: String, seasonId: String, queryParams: QueryParams = QueryParams()): PagingList<PlayerSeasonRanking> = client.get(Leagues.Id.Seasons.SeasonId(seasonId, Leagues.Id.Seasons(Leagues.Id(id)))) {
        addQueryParams(queryParams)
    }.bodyOrThrow<PagingList<PlayerSeasonRanking>>()

    suspend fun getLeagueSeasonOrNull(id: String, seasonId: String, queryParams: QueryParams = QueryParams()): PagingList<PlayerSeasonRanking>? = getOrNull { getLeagueSeason(id, seasonId, queryParams) }

    suspend fun getLeagueGroupSeason(leagueTag: String, seasonId: String, playerTag: String): LeagueGroup = client.get(LeagueGroups(leagueTag,seasonId)){
        url{ parameter("playerTag",playerTag) }
    }.bodyOrThrow<LeagueGroup>()

    @Deprecated(LEAGUE_DEPRECATION_MESSAGE)
    suspend fun getLeagues(queryParams: QueryParams = QueryParams()): PagingList<League> = client.get(Leagues()){
        addQueryParams(queryParams)
    }.bodyOrThrow<PagingList<League>>()

    @Deprecated(LEAGUE_DEPRECATION_MESSAGE)
    suspend fun getLeaguesOrNull(queryParams: QueryParams = QueryParams()): PagingList<League>? = getOrNull { getLeagues(queryParams) }

    @Deprecated(LEAGUE_DEPRECATION_MESSAGE)
    suspend fun getLeague(id: String): League = client.get(Leagues.Id(id)).bodyOrThrow<League>()

    @Deprecated(LEAGUE_DEPRECATION_MESSAGE)
    suspend fun getLeagueOrNull(id: String): League? = getOrNull { getLeague(id) }

    //endregion

    //region Location Endpoints

    suspend fun getLocations(queryParams: QueryParams = QueryParams()): PagingList<Location> = client.get(Locations()){
        addQueryParams(queryParams)
    }.bodyOrThrow<PagingList<Location>>()

    suspend fun getLocationsOrNull(queryParams: QueryParams = QueryParams()): PagingList<Location>? = getOrNull { getLocations(queryParams) }

    suspend fun getLocation(locationId: String): Location = client.get(Locations.Id(locationId)).bodyOrThrow<Location>()

    suspend fun getLocationOrNull(locationId: String): Location? = getOrNull { getLocation(locationId) }

    suspend fun getClansRankingForLocation(locationId: String, queryParams: QueryParams = QueryParams()): PagingList<ClanRanking> = client.get(Locations.Id.Rankings.Clans(locationId)){
        addQueryParams(queryParams)
    }.bodyOrThrow<PagingList<ClanRanking>>()

    suspend fun getClansRankingForLocationOrNull(locationId: String, queryParams: QueryParams = QueryParams()): PagingList<ClanRanking>? = getOrNull { getClansRankingForLocation(locationId, queryParams) }

    suspend fun getPlayersRankingsForLocation(locationId: String, queryParams: QueryParams = QueryParams()): PagingList<PlayerRanking> = client.get(Locations.Id.Rankings.Players(locationId)){
        addQueryParams(queryParams)
    }.bodyOrThrow<PagingList<PlayerRanking>>()

    suspend fun getPlayersRankingsForLocationOrNull(locationId: String, queryParams: QueryParams = QueryParams()): PagingList<PlayerRanking>? = getOrNull { getPlayersRankingsForLocation(locationId, queryParams) }

    suspend fun getPlayersBuilderBaseRankingsForLocation(locationId: String, queryParams: QueryParams = QueryParams()): PagingList<PlayerBuilderBaseRanking> = client.get(Locations.Id.Rankings.PlayersBuilderBase(locationId)){
        addQueryParams(queryParams)
    }.bodyOrThrow<PagingList<PlayerBuilderBaseRanking>>()

    suspend fun getPlayersBuilderBaseRankingsForLocationOrNull(locationId: String, queryParams: QueryParams = QueryParams()): PagingList<PlayerBuilderBaseRanking>? = getOrNull { getPlayersBuilderBaseRankingsForLocation(locationId, queryParams) }

    suspend fun getClansBuilderBaseRankingsForLocation(locationId: String, queryParams: QueryParams = QueryParams()): PagingList<ClanBuilderBaseRanking> = client.get(Locations.Id.Rankings.ClansBuilderBase(locationId)){
        addQueryParams(queryParams)
    }.bodyOrThrow<PagingList<ClanBuilderBaseRanking>>()

    suspend fun getClansBuilderBaseRankingsForLocationOrNull(locationId: String, queryParams: QueryParams = QueryParams()): PagingList<ClanBuilderBaseRanking>? = getOrNull { getClansBuilderBaseRankingsForLocation(locationId, queryParams) }

    suspend fun getClanCapitalsRankingForLocation(locationId: String, queryParams: QueryParams = QueryParams()): PagingList<ClanCapitalRanking> = client.get(Locations.Id.Rankings.Capitals(locationId)){
        addQueryParams(queryParams)
    }.bodyOrThrow<PagingList<ClanCapitalRanking>>()

    suspend fun getClanCapitalsRankingForLocationOrNull(locationId: String, queryParams: QueryParams = QueryParams()): PagingList<ClanCapitalRanking>? = getOrNull { getClanCapitalsRankingForLocation(locationId, queryParams) }

    //endregion

    //region GoldPass Endpoints
    suspend fun getCurrentGoldPassSeason(): GoldPassSeason = client.get(GoldPass.Seasons.Current()).bodyOrThrow()

    suspend fun getCurrentGoldPassSeasonOrNull(): GoldPassSeason? = getOrNull { getCurrentGoldPassSeason() }
    //endregion

    //region Esport Endpoints
    //endregion

    //region Label Endpoints

    suspend fun getPlayerLabels(params: QueryParams = QueryParams()): PagingList<Label> = client.get(Labels.Players()){
        addQueryParams(params)
    }.bodyOrThrow<PagingList<Label>>()

    suspend fun getPlayerLabelsOrNull(params: QueryParams = QueryParams()): PagingList<Label>? = getOrNull { getPlayerLabels(params) }

    suspend fun getClanLabels(params: QueryParams = QueryParams()): PagingList<Label> = client.get(Labels.Clans()){
        addQueryParams(params)
    }.bodyOrThrow<PagingList<Label>>()

    suspend fun getClanLabelsOrNull(params: QueryParams = QueryParams()): PagingList<Label>? = getOrNull { getClanLabels(params) }

    //endregion

    private suspend inline fun <reified T : Any> HttpResponse.bodyOrThrow(): T {
        if (status.isSuccess()) return body()
        throw KlashApiException(status, body<ClientErrorResponse>())
    }

    private suspend fun <T> getOrNull(get: suspend () -> T): T? = try {
        get()
    } catch (k: KlashApiException) {
        logger.error("Failed to execute request", k)
        null
    } catch (e: Exception) {
        throw e
    }

}
