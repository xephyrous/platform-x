import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val idToken: String,
    val refreshToken: String,
    val expiresIn: String
)

@Serializable
data class UserData(
    var username: String,
    var email: String,
)