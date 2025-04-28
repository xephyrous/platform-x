package org.xephyrous.data

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.xephyrous.UserRole

/**
 * Handles [HttpResponse] casting and error handling
 */
suspend inline fun <reified T, reified E> handleResponse(res: HttpResponse) : Result<T> {
    if (res.status != HttpStatusCode.OK) {
        return Result.failure(Exception(Json.decodeFromString<E>(res.body()).toString()))
    }

    return Result.success(Json.decodeFromString<T>(res.body()))
}

@Serializable
data class SignInResponse(
    val kind: String,
    val localId: String,
    val email: String,
    val displayName: String,
    val idToken: String,
    val registered: Boolean
)

@Serializable
data class SignupNewUserResponse (
    val kind: String,
    val idToken: String,
    val email: String,
    val refreshToken: String,
    val expiresIn: Long,
    val localId: String
)

@Serializable
data class FirebaseError(
    val error: FirebaseErrorData
)

@Serializable
data class FirebaseErrorData(
    val code: Int,
    val message: String,
    val errors: List<FirebaseErrorDetail>
)

@Serializable
data class FirebaseErrorDetail(
    val message: String,
    val domain: String,
    val reason: String
)

@Serializable
data class FirestoreDocument(
    val name: String,
    val fields: Map<String, FirestoreValue>,
    val createTime: String,
    val updateTime: String
) {
    inline fun <reified T> toObject(): T {
        val decoder = FirestoreDocumentDecoder()
        val rawJson = FirestoreDocumentDecoder.unwrapFields(fields)
        return decoder.decode(rawJson)
    }
}

@Serializable
data class FirestoreValue(
    val stringValue: String? = null,
    val integerValue: String? = null,
    val booleanValue: Boolean? = null,
    val doubleValue: Double? = null,
    val timestampValue: String? = null,
    val mapValue: FirestoreMapValue? = null,
    val arrayValue: FirestoreArrayValue? = null,
    val nullValue: String? = null
)

@Serializable
data class FirestoreMapValue(
    val fields: Map<String, FirestoreValue>
)

@Serializable
data class FirestoreArrayValue(
    val values: List<FirestoreValue>
)

@Serializable
data class GoogleUserInfo(
    val sub: String,
    val picture: String,
    val email: String,
    val email_verified: Boolean
)

@Serializable
data class GoogleError(
    val error: GoogleErrorDetail
)

@Serializable
data class GoogleErrorDetail(
    val code: Int,
    val message: String,
    val status: String
)

@Serializable
data class FirebaseUserInfo(
    val federatedId: String,
    val providerId: String,
    val email: String,
    val emailVerified: Boolean,
    val photoUrl: String,
    val localId: String,
    val idToken: String,
    val oauthAccessToken: String,
    val oauthExpireIn: Int,
    val refreshToken: String,
    val expiresIn: String,
    val rawUserInfo: String,
    val kind: String
)

@Serializable
data class UserData(
    val role: UserRole,
)

@Serializable
data class CourseData(
    val courseNumber: Int,
    val coursePrefix: String,
    val description: String,
    val time: String,
    val location: String,
    val instructor: String
)

@Serializable
data class EventData(
    val name: String,
    val description: String,
    val location: String,
    val time: LocaleDate,
)

@Serializable
data class LocaleDate(
    val month: Int,
    val year: Int,
    val day: Int
)