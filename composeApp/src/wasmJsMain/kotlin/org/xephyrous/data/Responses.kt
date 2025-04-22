package org.xephyrous.data

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * Handles [HttpResponse] casting and error handling
 */
suspend inline fun <reified T, reified E> handleResponse(res: HttpResponse) : Result<T> {
    println(Json.encodeToString(res.toString()))

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
data class UserData(
    var username: String,
    var email: String,
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
data class TestDocument(
    val data: String
)

@Serializable
data class UserInfo(
    val sub: String,
    val email: String,
    val emailVerified: Boolean,
    val name: String,
    val picture: String,
    val givenName: String,
    val familyName: String,
    val locale: String
)

@Serializable
data class GoogleError(
    val smth: String
)