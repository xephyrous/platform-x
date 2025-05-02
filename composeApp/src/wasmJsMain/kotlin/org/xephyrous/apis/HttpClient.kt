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

/**
 * Singleton object that provides HTTP client utilities for performing RESTful API requests.
 */
object HttpClient {
    /**
     * A pre-configured Ktor [HttpClient] instance for making requests with JSON support.
     */
    val client = HttpClient(Js) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    /**
     * Performs an HTTP GET request.
     *
     * @param url The URL to send the request to.
     * @param headers Optional HTTP headers.
     * @param params Optional query parameters.
     *
     * @return The deserialized response body of type [T].
     */
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

    /**
     * Performs an HTTP POST request.
     *
     * @param url The endpoint to which the POST request is sent.
     * @param body The body payload to send.
     * @param headers Optional HTTP headers.
     * @param params Optional query parameters.
     *
     * @return The raw [HttpResponse].
     */
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

    /**
     * Performs an HTTP PUT request.
     *
     * @param url The endpoint to which the PUT request is sent.
     * @param body The request payload.
     * @param headers Optional HTTP headers.
     *
     * @return The raw [HttpResponse].
     */
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

    /**
     * Performs an HTTP DELETE request.
     *
     * @param url The URL of the resource to delete.
     * @param headers Optional HTTP headers.
     *
     * @return The raw [HttpResponse].
     */
    suspend fun delete(
        url: String,
        headers: Map<String, String> = emptyMap()
    ): HttpResponse {
        return client.delete(url) {
            headers.forEach { (key, value) -> header(key, value) }
        }
    }

    /**
     * Performs an HTTP PATCH request.
     *
     * @param url The endpoint to which the PATCH request is sent.
     * @param body The body payload to send.
     * @param headers Optional HTTP headers.
     * @param params Optional query parameters.
     *
     * @return The raw [HttpResponse].
     */
    suspend inline fun <reified T : Any> patch(
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

        return client.patch(finalUrl) {
            contentType(ContentType.Application.Json)
            headers.forEach { (key, value) -> header(key, value) }
            setBody(Json.encodeToString(body))  // serialize body to JSON
        }
    }
}
