package org.xephyrous.apis

import io.ktor.client.statement.*
import kotlinx.serialization.json.Json
import org.xephyrous.data.Secrets

object Firebase {
    object Auth {
        private const val ENDPOINT = "https://identitytoolkit.googleapis.com/v1/"

        suspend fun signInWithPassword(email: String, password: String, returnSecureToken: Boolean = false) : HttpResponse {
            return HttpClient.post(
                "${ENDPOINT}accounts:signInWithPassword?key=${Secrets.FIREBASE_API_KEY}",
                mapOf(
                    "email" to email,
                    "password" to password,
                    "returnSecureToken" to returnSecureToken.toString()
                )
            )
        }

        suspend fun getDocument(path: String, mask: Array<String>, transaction: String, readTime: String) {
            return HttpClient.get(
                "${ENDPOINT}name=projects/${Secrets.FIREBASE_PROJECT_ID}/databases/(default)/documents/$path",
                mapOf(
                    "mask" to "{ \"fieldPaths\": ${Json.encodeToString(mask)} }"
                )
            )
        }
    }
}