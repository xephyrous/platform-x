package org.xephyrous.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.double
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.long
import kotlinx.serialization.json.longOrNull
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

inline fun <reified T> encodeFirestoreFields(document: T, serializer: KSerializer<T>): Map<String, Any> {
    val jsonObject = Json.encodeToJsonElement(serializer, document).jsonObject
    return jsonObject.mapValues { (key, value) ->
        when (value) {
            is JsonPrimitive -> when {
                value.isString -> mapOf("stringValue" to value.content)
                value.booleanOrNull != null -> mapOf("booleanValue" to value.boolean)
                value.longOrNull != null -> mapOf("integerValue" to value.long)
                value.doubleOrNull != null -> mapOf("doubleValue" to value.double)
                else -> error("Unsupported primitive type for field: $key")
            }
            is JsonObject -> mapOf("mapValue" to mapOf("fields" to encodeFirestoreFields(value)))
            is JsonArray -> mapOf("arrayValue" to mapOf("values" to value.map { encodeFirestoreFields(it) }))
        }
    }
}

fun encodeFirestoreFields(value: JsonElement): Any {
    return when (value) {
        is JsonPrimitive -> when {
            value.isString -> mapOf("stringValue" to value.content)
            value.booleanOrNull != null -> mapOf("booleanValue" to value.boolean)
            value.longOrNull != null -> mapOf("integerValue" to value.long)
            value.doubleOrNull != null -> mapOf("doubleValue" to value.double)
            else -> error("Unsupported primitive type")
        }
        is JsonObject -> mapOf("mapValue" to mapOf("fields" to encodeFirestoreFields(value)))
        is JsonArray -> mapOf("arrayValue" to mapOf("values" to value.map { encodeFirestoreFields(it) }))
    }
}

fun encodeFirestoreFields(json: JsonObject): Map<String, Any> {
    return json.mapValues { (_, value) -> encodeFirestoreFields(value) }
}

fun encodeFirestoreValueToJsonElement(value: Any): JsonElement {
    return when (value) {
        is Map<*, *> -> {
            JsonObject(
                value.mapNotNull { (k, v) ->
                    if (k is String && v != null) {
                        k to encodeFirestoreValueToJsonElement(v)
                    } else {
                        null
                    }
                }.toMap()
            )
        }
        is List<*> -> JsonArray(
            value.filterNotNull().map { encodeFirestoreValueToJsonElement(it) }
        )
        is String -> JsonPrimitive(value)
        is Number -> JsonPrimitive(value)
        is Boolean -> JsonPrimitive(value)
        else -> JsonPrimitive(value.toString())
    }
}