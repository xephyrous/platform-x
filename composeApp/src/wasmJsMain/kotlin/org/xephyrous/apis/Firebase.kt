package org.xephyrous.apis

import org.xephyrous.data.FirebaseError
import org.xephyrous.data.SignInResponse
import org.xephyrous.data.SignupNewUserResponse
import io.ktor.client.call.*
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.Json
import org.xephyrous.data.Secrets
import org.xephyrous.data.handleResponse

typealias DocumentMask = Array<String>

object Firebase {
    object Auth {
        const val ENDPOINT = "https://identitytoolkit.googleapis.com/v1/"

        suspend fun signInWithPassword(
            email: String,
            password: String,
            returnSecureToken: Boolean = false
        ) : Result<SignInResponse> {
            return handleResponse<SignInResponse, FirebaseError>(
                HttpClient.post(
                    "${ENDPOINT}accounts:signInWithPassword?key=${Secrets.FIREBASE_API_KEY}",
                    mapOf(
                        "email" to email,
                        "password" to password,
                        "returnSecureToken" to returnSecureToken.toString()
                    )
                )
            )
        }

        suspend fun signUpWithPassword(
            email: String,
            password: String,
            returnSecureToken: Boolean = false
        ) : Result<SignupNewUserResponse> {
            return handleResponse<SignupNewUserResponse, FirebaseError>(
                HttpClient.post(
                    "${ENDPOINT}accounts:signUp?key=${Secrets.FIREBASE_API_KEY}",
                    mapOf(
                        "email" to email,
                        "password" to password,
                        "returnSecureToken" to returnSecureToken.toString()
                    )
                )
            )
        }
    }

    object Firestore {
        const val ENDPOINT = "https://firestore.googleapis.com/v1/"

        // TODO
        suspend inline fun <reified T> getDocument(
            path: String,
            idToken: String,
            mask: DocumentMask? = null,
            transaction: String? = null,
            readTime: String? = null
        ) : Result<T> {
            return handleResponse<T, FirebaseError>(
                HttpClient.get(
                    "${ENDPOINT}projects/${Secrets.FIREBASE_PROJECT_ID}/databases/(default)/documents/$path",
                    buildMap {
                        put("Authorization", "Bearer $idToken")
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
            idToken: String,
            mask: DocumentMask? = null
        ) : T {
            return HttpClient.post(
                "${ENDPOINT}projects/${Secrets.FIREBASE_PROJECT_ID}/databases/(default)/documents/$collectionId",
                Json.encodeToString(document),
                buildMap {
                    put("documentId", documentId)
                    put("Authorization", "Bearer $idToken")
                    mask?.let { put("mask", "{ \"fieldPaths\": ${Json.encodeToString(mask)} }") }
                }
            ).body()
        }
    }
}