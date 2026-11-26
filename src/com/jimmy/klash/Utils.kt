package com.jimmy.klash

import com.jimmy.klash.api.QueryParams
import com.jimmy.klash.model.ClanWarResponse
import io.ktor.client.request.*
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss.SSSZ")
private val parsingDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss.SSSXX")

fun String.asTag() = if(this.startsWith("#")) this else "#$this"

fun HttpRequestBuilder.addQueryParams(queryParams: QueryParams){
    url{
        queryParams.getParams().forEach { parameters.append(it.first, it.second) }
    }
}

class OffsetDateTimeSerializer: KSerializer<OffsetDateTime>{
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("OffsetDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: OffsetDateTime) {
        encoder.encodeString(value.format(dateTimeFormatter))
    }

    override fun deserialize(decoder: Decoder): OffsetDateTime {
        return OffsetDateTime.parse(decoder.decodeString(), parsingDateTimeFormatter)
    }
}

class IsoOffsetDateTimeSerializer: KSerializer<OffsetDateTime>{
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("IsoOffsetDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: OffsetDateTime) {
        encoder.encodeString(value.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
    }

    override fun deserialize(decoder: Decoder): OffsetDateTime {
        return OffsetDateTime.parse(decoder.decodeString(), DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }
}

object ClanWarResponseSerializer : JsonContentPolymorphicSerializer<ClanWarResponse>(ClanWarResponse::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<ClanWarResponse> {
        val stateValue = element.jsonObject["state"]?.jsonPrimitive?.content

        return if (stateValue == "notInWar") {
            ClanWarResponse.NotInWar.serializer()
        } else {
            ClanWarResponse.ClanWar.serializer()
        }
    }
}