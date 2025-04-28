package org.xephyrous.apis

import org.xephyrous.data.FirebaseError
import org.xephyrous.data.SignInResponse
import org.xephyrous.data.SignupNewUserResponse
import io.ktor.client.call.*
import io.ktor.client.statement.bodyAsText
import kotlinx.browser.window
import kotlinx.serialization.json.Json
import org.xephyrous.data.FirebaseUserInfo
import org.xephyrous.data.FirestoreDocument
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

        suspend fun signInWithOAuth(
            accessToken: String
        ) : Result<FirebaseUserInfo> {
            val res = HttpClient.post(
                "${ENDPOINT}accounts:signInWithIdp?key=${Secrets.FIREBASE_API_KEY}",
                mapOf(
                    "postBody" to "access_token=$accessToken&providerId=google.com",
                    "requestUri" to window.location.origin,
                    "returnSecureToken" to true.toString(),
                    "returnIdpCredential" to true.toString()
                )
            )

            val text = res.bodyAsText()  // 👈 Read the raw body as plain text
            println(text)

            return handleResponse<FirebaseUserInfo, FirebaseError>(
                res
            )
        }
    }

    object Firestore {
        const val ENDPOINT = "https://firestore.googleapis.com/v1/"

        // TODO
        suspend inline fun getDocument(
            path: String,
            idToken: String,
            mask: DocumentMask? = null,
            transaction: String? = null,
            readTime: String? = null
        ) : Result<FirestoreDocument> {
            return handleResponse<FirestoreDocument, FirebaseError>(
                HttpClient.get(
                    "${ENDPOINT}projects/${Secrets.FIREBASE_PROJECT_ID}/databases/(default)/documents/$path",
                    headers = mapOf("Authorization" to "Bearer $idToken"),
                    params = buildMap {
                        mask?.let { put("mask.fieldPaths", Json.encodeToString(it)) }
                        transaction?.let { put("transaction", it) }
                        readTime?.let { put("readTime", it) }
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