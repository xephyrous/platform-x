package org.xephyrous.apis

import org.xephyrous.data.FirebaseError
import org.xephyrous.data.SignInResponse
import org.xephyrous.data.SignupNewUserResponse
import kotlinx.browser.window
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

/** Alias for a list of Firestore field names used as a field mask. */
typealias DocumentMask = Array<String>

/** Firebase singleton object providing authentication and Firestore access functions. */
object Firebase {

    /** Default maximum page size for document listing. */
    const val PAGE_SIZE = 10_000

    /** Handles Firebase Authentication requests. */
    object Auth {
        /** Base endpoint for Firebase Identity Toolkit API. */
        const val ENDPOINT = "https://identitytoolkit.googleapis.com/v1/"

        /**
         * Signs in a user using email and password credentials.
         *
         * @param email The user's email address.
         * @param password The user's password.
         * @param returnSecureToken Whether to return an ID and refresh token.
         *
         * @return A [Result] containing [SignInResponse] on success or [FirebaseError] on failure.
         */
        suspend fun signInWithPassword(
            email: String,
            password: String,
            returnSecureToken: Boolean = false
        ): Result<SignInResponse> {
            return handleResponse(
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

        /**
         * Signs up a new user with email and password.
         *
         * @param email The user's email address.
         * @param password The user's password.
         * @param returnSecureToken Whether to return an ID and refresh token.
         *
         * @return A [Result] containing [SignupNewUserResponse] or [FirebaseError].
         */
        suspend fun signUpWithPassword(
            email: String,
            password: String,
            returnSecureToken: Boolean = false
        ): Result<SignupNewUserResponse> {
            return handleResponse(
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

        /**
         * Signs in a user using a Google OAuth 2.0 access token.
         *
         * @param accessToken The access token received from Google.
         *
         * @return A [Result] containing [FirebaseUserInfo] or [FirebaseError].
         */
        suspend fun signInWithOAuth(
            accessToken: String
        ): Result<FirebaseUserInfo> {
            return handleResponse(
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

    /** Provides CRUD operations for Firestore documents. */
    object Firestore {
        /** Base endpoint for Firebase Firestore REST API. */
        const val ENDPOINT = "https://firestore.googleapis.com/v1/"

        /**
         * Retrieves a document from Firestore.
         *
         * @param path Document path in the format `collection/document`.
         * @param idToken The user's ID token for authentication.
         * @param mask Optional list of fields to retrieve.
         * @param transaction Optional transaction identifier.
         * @param readTime Optional timestamp to read the document at a specific time.
         *
         * @return A [Result] containing the [FirestoreDocument] or [FirebaseError].
         */
        suspend inline fun getDocument(
            path: String,
            idToken: String,
            mask: DocumentMask? = null,
            transaction: String? = null,
            readTime: String? = null
        ): Result<FirestoreDocument> {
            return handleResponse(
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

        /**
         * Creates or overwrites a document in Firestore.
         *
         * @param collectionId The collection in which to create the document.
         * @param documentId The ID of the document to create.
         * @param document The object to encode as the document's fields.
         * @param idToken The user's ID token for authentication.
         *
         * @return A [Result] containing the created [FirestoreDocument] or [FirebaseError].
         */
        suspend inline fun <reified T> createDocument(
            collectionId: String,
            documentId: String,
            document: T,
            idToken: String
        ): Result<FirestoreDocument> {
            val firestoreFields = encodeFirestoreFields(document, serializer())

            val fieldsJson = JsonObject(
                firestoreFields.mapValues { (_, value) ->
                    encodeFirestoreValueToJsonElement(value)
                }
            )

            return handleResponse(
                HttpClient.patch(
                    "${ENDPOINT}projects/${Secrets.FIREBASE_PROJECT_ID}/databases/(default)/documents/$collectionId/$documentId",
                    body = JsonObject(mapOf("fields" to fieldsJson)),
                    headers = mapOf(
                        "Authorization" to "Bearer $idToken",
                        "Content-Type" to "application/json"
                    )
                )
            )
        }

        /**
         * Lists documents in a Firestore collection.
         *
         * @param path Collection path.
         * @param idToken The user's ID token for authentication.
         * @param pageSize Maximum number of documents to return.
         * @param pageToken Token for pagination.
         * @param orderBy Optional field to order by.
         * @param mask Optional field mask.
         * @param transaction Optional transaction identifier.
         * @param readTime Optional timestamp to read at a specific time.
         *
         * @return A [Result] containing a map of document IDs to decoded objects, or [FirebaseError].
         */
        suspend inline fun <reified T> listDocuments(
            path: String,
            idToken: String,
            pageSize: Int = PAGE_SIZE,
            pageToken: String? = null,
            orderBy: String? = null,
            mask: DocumentMask? = null,
            transaction: String? = null,
            readTime: String? = null
        ): Result<Map<String, T>> {
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

        /**
         * Updates an existing Firestore document with new data.
         *
         * @param path Full path to the document.
         * @param idToken The user's ID token for authentication.
         * @param data Object to encode as the document fields.
         *
         * @return A [Result] containing the updated [FirestoreDocument] or [FirebaseError].
         */
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

            return handleResponse(
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

        /**
         * Deletes a Firestore document.
         *
         * @param path Path to the document to delete.
         * @param idToken The user's ID token for authentication.
         *
         * @return A [Result] indicating success or containing a [FirebaseError].
         */
        suspend inline fun deleteUser(
            path: String,
            idToken: String
        ): Result<Unit> {
            return handleResponse(
                HttpClient.delete(
                    "${ENDPOINT}projects/${Secrets.FIREBASE_PROJECT_ID}/databases/(default)/documents/$path",
                    headers = mapOf("Authorization" to "Bearer $idToken")
                )
            )
        }
    }
}
