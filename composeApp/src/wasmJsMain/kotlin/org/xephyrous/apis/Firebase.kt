package org.xephyrous.apis

import AuthResponse
import io.ktor.client.call.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.xephyrous.apis.Firebase.Auth.ENDPOINT
import org.xephyrous.data.Secrets

typealias DocumentMask = Array<String>

object Firebase {
    object Auth {
        const val ENDPOINT = "https://identitytoolkit.googleapis.com/v1/"

        suspend fun signInWithPassword(
            email: String,
            password: String,
            returnSecureToken: Boolean = false
        ) : HttpResponse {
            return HttpClient.post(
                "${ENDPOINT}accounts:signInWithPassword?key=${Secrets.FIREBASE_API_KEY}",
                mapOf(
                    "email" to email,
                    "password" to password,
                    "returnSecureToken" to returnSecureToken.toString()
                )
            )
        }
    }

    object Firestore {
        const val ENDPOINT = "https://firestore.googleapis.com/v1/"

        // TODO
        suspend fun <T> getDocument(
            path: String,
            mask: DocumentMask? = null,
            transaction: String? = null,
            readTime: String? = null
        ) {
            return Json.decodeFromString(
                HttpClient.get(
                    "${ENDPOINT}projects/${Secrets.FIREBASE_PROJECT_ID}/databases/(default)/documents/$path",
                    buildMap {
                        mask?.let { put("mask", "{ \"fieldPaths\": ${Json.encodeToString(mask)} }") }
                        transaction?.let { put("transaction", transaction) }
                        readTime?.let { put("readTime", readTime) }
                    }
                )
            )
        }

        suspend inline fun <reified T> createDocument(
            collectionId: String,
            documentId: String,
            document: T,
            mask: DocumentMask? = null
        ) : T {
            return HttpClient.post(
                "${ENDPOINT}projects/${Secrets.FIREBASE_PROJECT_ID}/databases/(default)/documents/$collectionId",
                Json.encodeToString(document),
                buildMap {
                    put("documentId", documentId)
                    mask?.let { put("mask", "{ \"fieldPaths\": ${Json.encodeToString(mask)} }") }
                }
            ).body()
        }
    }
}