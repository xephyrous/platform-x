package org.xephyrous.apis

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.xephyrous.js.encodeURIComponent

object HttpClient {
    val client = HttpClient(Js) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend inline fun <reified T> get(
        url: String,
        headers: Map<String, String> = emptyMap(),
        params: Map<String, String> = emptyMap()
    ): T {
        val response = client.get(url) {
            headers.forEach { (key, value) -> header(key, value) }
            url {
                params.forEach { (key, value) -> parameters.append(key, value) }
            }
        }
        return response.body()
    }

    suspend inline fun <reified T : Any> post(
        url: String,
        body: T,
        headers: Map<String, String> = emptyMap(),
        params: Map<String, String> = emptyMap()
    ): HttpResponse {
        val finalUrl = if (params.isNotEmpty()) {
            val encodedParams = params.entries.joinToString("&") {
                "${encodeURIComponent(it.key)}=${encodeURIComponent(it.value)}"
            }
            "$url?$encodedParams"
        } else {
            url
        }

        return client.post(finalUrl) {
            contentType(ContentType.Application.Json)
            headers.forEach { (key, value) -> header(key, value) }
            setBody(Json.encodeToString(body))  // Ensure body is serialized to JSON string
        }
    }

    suspend inline fun <reified T : Any> put(
        url: String,
        body: T,
        headers: Map<String, String> = emptyMap()
    ): HttpResponse {
        return client.put(url) {
            contentType(ContentType.Application.Json)
            headers.forEach { (key, value) -> header(key, value) }
            setBody(body)
        }
    }

    suspend fun delete(
        url: String,
        headers: Map<String, String> = emptyMap()
    ): HttpResponse {
        return client.delete(url) {
            headers.forEach { (key, value) -> header(key, value) }
        }
    }
}