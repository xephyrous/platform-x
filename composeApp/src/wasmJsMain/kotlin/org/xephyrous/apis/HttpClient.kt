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
        headers: Map<String, String> = emptyMap()
    ): T {
        val response = client.get(url) {
            headers.forEach { (key, value) -> header(key, value) }
        }
        return response.body()
    }

    suspend inline fun <reified T : Any> post(
        url: String,
        body: T,
        headers: Map<String, String> = emptyMap()
    ): HttpResponse {
        return client.post(url) {
            contentType(ContentType.Application.Json)
            headers.forEach { (key, value) -> header(key, value) }
            setBody(body)
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