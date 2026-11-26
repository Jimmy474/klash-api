package com.jimmy.klash.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Role{
    @SerialName("notMember") NotMember,
    @SerialName("member") Member,
    @SerialName("leader") Leader,
    @SerialName("admin") Admin,
    @SerialName("coLeader") Coleader
}