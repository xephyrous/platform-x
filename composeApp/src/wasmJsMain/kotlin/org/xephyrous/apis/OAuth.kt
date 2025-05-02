package org.xephyrous.apis

import kotlinx.browser.window
import org.xephyrous.data.Secrets
import org.xephyrous.data.GoogleUserInfo
import org.xephyrous.data.handleResponse

/**
 * Singleton object to handle OAuth 2.0 authentication flow with Google.
 */
object OAuth {
    /**
     * Google OAuth 2.0 authorization endpoint.
     */
    const val ENDPOINT = "https://accounts.google.com/o/oauth2/v2/auth"

    /**
     * Initiates the OAuth 2.0 authorization redirect to Google's OAuth endpoint.
     *
     * @param scope Scopes defining the access the application is requesting.
     * @param state Optional state to be returned with the response.
     * @param includeGrantedScopes Whether to include previously granted scopes.
     * @param enableGranularConsent Enables user selection of individual scopes.
     * @param loginHint Optional email address or sub identifier to pre-fill the login form.
     * @param prompt A list of prompt values to control the login experience.
     */
    fun redirect(
        scope: Array<String>,
        state: String? = null,
        includeGrantedScopes: Boolean? = null,
        enableGranularConsent: Boolean? = null,
        loginHint: String? = null,
        prompt: Array<String>? = null
    ) {
        window.location.href = buildURI(
            ENDPOINT,
            buildMap {
                put("client_id", Secrets.OAUTH_CLIENT_ID)
                put("redirect_uri", window.location.origin)
                put("response_type", "token")
                put("scope", scope.joinToString(" "))
                state?.let { put("state", it) }
                includeGrantedScopes?.let { put("include_granted_scopes", it.toString()) }
                enableGranularConsent?.let { put("enable_granular_consent", it.toString()) }
                loginHint?.let { put("login_hint", it) }
                prompt?.let { put("prompt", prompt.joinToString(" ")) }
            }
        )
    }

    /**
     * Fetches the authenticated user's profile information from Google.
     *
     * @param token The OAuth 2.0 bearer token.
     * @return A [Result] wrapping the [GoogleUserInfo] if successful.
     */
    suspend fun getUserInfo(
        token: String
    ) : Result<GoogleUserInfo> {
        return handleResponse<GoogleUserInfo, String>(
            HttpClient.get(
                "https://www.googleapis.com/oauth2/v3/userinfo",
                headers = buildMap { put("Authorization", "Bearer $token") }
            )
        )
    }
}
