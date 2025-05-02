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

/**
 * Used for dynamic decoding of Firestore document JSON
 *
 * @see [FirestoreDocument]
 */
class FirestoreDocumentDecoder(val json: Json = Json.Default) {
    /**
     * Decodes a [JsonObject] into a dynamic type
     *
     * @param fieldsJson The JSON object to decode
     *
     * @return T: The decoded object
     */
    inline fun <reified T> decode(fieldsJson: JsonObject): T {
        return json.decodeFromJsonElement(serializer(), fieldsJson)
    }

    companion object {
        /**
         * Converts a map of JSON fields, as [FirestoreValue]s, to a [JsonObject]
         *
         * @param fields A map of variable names to values
         *
         * @return [JsonObject]: The built object
         */
        fun unwrapFields(fields: Map<String, FirestoreValue>): JsonObject {
            return buildJsonObject {
                fields.forEach { (key, value) ->
                    put(key, value.unwrap())
                }
            }
        }
    }
}

/**
 * Unwraps a Firestore JSON element into a [JsonPrimitive]
 *
 * @return [JsonPrimitive] or [JsonNull]: The unwrapped data element
 */
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

/**
 * Dynamically encodes a document into a map of variable names to values
 *
 * @param document The document to encode
 * @param serializer The local serializer to use during encoding
 *
 * @return [Map]: The encoded map object
 */
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

/**
 * Encodes a [JsonElement] to a mapping of name to value(s)
 *
 * @param value The [JsonElement] to encode
 *
 * @return [Any] The encoded field
 */
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

/**
 * Encodes a [JsonObject] to a mapping of name to value(s)
 *
 * @param json The [JsonObject] to encode
 *
 * @return [Map] The encoded fields
 */
fun encodeFirestoreFields(json: JsonObject): Map<String, Any> {
    return json.mapValues { (_, value) -> encodeFirestoreFields(value) }
}

/**
 * Encodes a value to a [JsonElement]
 *
 * @param value The value to encode
 *
 * @return [JsonElement]: The encoded value
 */
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