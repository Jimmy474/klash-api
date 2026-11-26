package com.jimmy.klash

import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime
import kotlin.test.assertEquals

class OffsetDateTimeSerializerTests {
    @Test
    fun yearBoundaryRoundTrip() {
        val serializer = OffsetDateTimeSerializer()
        val timestamp = "20241231T120000.000+0000"
        val expected = OffsetDateTime.parse("2024-12-31T12:00:00Z")
        val json = "\"$timestamp\""

        assertEquals(expected, Json.decodeFromString(serializer, json))
        assertEquals(json, Json.encodeToString(serializer, expected))
        assertEquals(expected, Json.decodeFromString(serializer, Json.encodeToString(serializer, expected)))
    }
}
