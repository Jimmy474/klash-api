package com.jimmy.klash.api

import com.jimmy.klash.model.WarFrequency

open class QueryParams(
    open val limit: Int? = null,
    open val before: String? = null,
    open val after: String? = null
){
    protected val paramPairs: MutableList<Pair<String, String>> = mutableListOf()

    fun getParams(): List<Pair<String, String>> = paramPairs.toList()

    init{
        limit?.let {
            require(it >= 1){ "Limit must be greater than or equal to 1" }
            paramPairs.add("limit" to "$limit")
        }
        if(before != null && after != null) throw IllegalArgumentException("Cannot specify both before and after only 1 is allowed")
        before?.let{ paramPairs.add("before" to it) }
        after?.let{ paramPairs.add("after" to it) }
    }
}

class ClanQueryParams(
    limit: Int? = null,
    before: String? = null,
    after: String? = null,
    val name: String? = null,
    warFrequency: WarFrequency? = null,
    locationId: Int? = null,
    minMembers: Int? = null,
    maxMembers: Int? = null,
    minClanPoints: Int? = null,
    minClanLevel: Int? = null,
    labelIds: List<Int>? = null
): QueryParams(limit, before, after){
    init {
        name?.let{
            require(it.length >= 2){ "Name must be at least 2 characters long" }
            paramPairs.add("name" to it)
        }
        warFrequency?.let{ frequency ->
            paramPairs.add("warFrequency" to frequency.name.replaceFirstChar { it.lowercase() })
        }
        locationId?.let{
            paramPairs.add("locationId" to "$it")
        }
        minMembers?.let{
            require(it in 2..50){ "Min members must be between 2 and 50" }
            paramPairs.add("minMembers" to "$it")
        }
        maxMembers?.let{
            require(it in 1..50){ "Max members must be between 1 and 50" }
            paramPairs.add("maxMembers" to "$it")
        }
        minClanPoints?.let{
            require(it >= 0){ "Min clan points must be greater than or equal to 0" }
            paramPairs.add("minClanPoints" to "$it")
        }
        minClanLevel?.let{
            require(it >= 1){ "Min clan level must be greater than or equal to 1" }
            paramPairs.add("minClanLevel" to "$it")
        }
        labelIds?.takeIf { it.isNotEmpty() }?.let{
            paramPairs.add("labelIds" to it.joinToString(","))
        }

        require(name != null || warFrequency != null || locationId != null || minMembers != null || maxMembers != null || minClanPoints != null || minClanLevel != null || labelIds != null){ "At least one query parameter must be specified" }
    }
}
