package org.xephyrous.apis

import org.xephyrous.data.FirebaseError
import org.xephyrous.data.SignInResponse
import org.xephyrous.data.SignupNewUserResponse
import kotlinx.browser.window
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.serializer
import org.xephyrous.data.FirebaseUserInfo
import org.xephyrous.data.FirestoreDocument
import org.xephyrous.data.FirestoreListDocumentsResponse
import org.xephyrous.data.Secrets
import org.xephyrous.data.encodeFirestoreFields
import org.xephyrous.data.encodeFirestoreValueToJsonElement
import org.xephyrous.data.handleResponse

typealias DocumentMask = Array<String>

object Firebase {
    const val PAGE_SIZE = 10_000

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
            return handleResponse<FirebaseUserInfo, FirebaseError>(
                HttpClient.post(
                    "${ENDPOINT}accounts:signInWithIdp?key=${Secrets.FIREBASE_API_KEY}",
                    mapOf(
                        "postBody" to "access_token=$accessToken&providerId=google.com",
                        "requestUri" to window.location.origin,
                        "returnSecureToken" to true.toString(),
                        "returnIdpCredential" to true.toString()
                    )
                )
            )
        }
    }

    object Firestore {
        const val ENDPOINT = "https://firestore.googleapis.com/v1/"

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
                        mask?.let { put("mask", Json.encodeToString(it)) }
                        transaction?.let { put("transaction", it) }
                        readTime?.let { put("readTime", it) }
                    }
                )
            )
        }

//        suspend inline fun <reified T> createDocument(
//            collectionId: String,
//            documentId: String,
//            document: T,
//            idToken: String,
//            mask: DocumentMask? = null
//        ) : T {
//            return HttpClient.post(
//                "${ENDPOINT}projects/${Secrets.FIREBASE_PROJECT_ID}/databases/(default)/documents/$collectionId/$documentId",
//                body = mapOf("fields" to encodeFirestoreFields(document, serializer())),
//                headers = mapOf("Authorization" to "Bearer $idToken")
//            ).body()
//        }

        suspend inline fun <reified T> listDocuments(
            path: String,
            idToken: String,
            pageSize: Int = PAGE_SIZE,
            pageToken: String? = null,
            orderBy: String? = null,
            mask: DocumentMask? = null,
            transaction: String? = null,
            readTime: String? = null
        ) : Result<Map<String, T>> {
            return handleResponse<FirestoreListDocumentsResponse, FirebaseError>(
                HttpClient.get(
                    "${ENDPOINT}projects/${Secrets.FIREBASE_PROJECT_ID}/databases/(default)/documents/$path",
                    headers = mapOf("Authorization" to "Bearer $idToken"),
                    params = buildMap {
                        put("pageSize", pageSize.toString())
                        pageToken?.let { put("pageToken", it) }
                        orderBy?.let { put("orderBy", it) }
                        mask?.let { put("mask", Json.encodeToString(it)) }
                        transaction?.let { put("transaction", it) }
                        readTime?.let { put("readTime", it) }
                    }
                )
            ).map { response ->
                response.documents.mapNotNull { doc ->
                    if (doc.fields.isEmpty()) return@mapNotNull null
                    try {
                        val id = doc.name.substringAfterLast("/")
                        id to doc.toObject<T>()
                    } catch (_: Exception) {
                        null
                    }
                }.toMap()
            }
        }

        suspend inline fun <reified T> updateDocument(
            path: String,
            idToken: String,
            data: T
        ): Result<FirestoreDocument> {
            val firestoreFields = encodeFirestoreFields(data, serializer())

            val fieldsJson = JsonObject(
                firestoreFields.mapValues { (_, value) ->
                    encodeFirestoreValueToJsonElement(value)
                }
            )

            return handleResponse<FirestoreDocument, FirebaseError>(
                HttpClient.patch(
                    "${ENDPOINT}projects/${Secrets.FIREBASE_PROJECT_ID}/databases/(default)/documents/$path",
                    body = JsonObject(mapOf("fields" to fieldsJson)),
                    headers = mapOf(
                        "Authorization" to "Bearer $idToken",
                        "Content-Type" to "application/json"
                    )
                )
            )
        }

        suspend inline fun deleteUser(
            path: String,
            idToken: String
        ): Result<Unit> {
            return handleResponse<Unit, FirebaseError>(
                HttpClient.delete(
                    "${ENDPOINT}projects/${Secrets.FIREBASE_PROJECT_ID}/databases/(default)/documents/$path",
                    headers = mapOf("Authorization" to "Bearer $idToken")
                )
            )
        }
    }
}