package org.xephyrous.data

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.serializer

class FirestoreDocumentDecoder(val json: Json = Json.Default) {
    inline fun <reified T> decode(fieldsJson: JsonObject): T {
        return json.decodeFromJsonElement(serializer(), fieldsJson)
    }

    companion object {
        fun unwrapFields(fields: Map<String, FirestoreValue>): JsonObject {
            return buildJsonObject {
                fields.forEach { (key, value) ->
                    put(key, value.unwrap())
                }
            }
        }
    }
}

fun FirestoreValue.unwrap(): JsonElement {
    return when {
        stringValue != null -> JsonPrimitive(stringValue)
        integerValue != null -> JsonPrimitive(integerValue.toLong())
        booleanValue != null -> JsonPrimitive(booleanValue)
        doubleValue != null -> JsonPrimitive(doubleValue)
        timestampValue != null -> JsonPrimitive(timestampValue)
        mapValue != null -> FirestoreDocumentDecoder.unwrapFields(mapValue.fields)
        arrayValue != null -> JsonArray(arrayValue.values.map { it.unwrap() })
        nullValue != null -> JsonNull
        else -> JsonNull
    }
}