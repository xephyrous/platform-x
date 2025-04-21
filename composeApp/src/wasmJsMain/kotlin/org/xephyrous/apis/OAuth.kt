package org.xephyrous.apis

import kotlinx.browser.window
import org.xephyrous.data.Secrets
import org.xephyrous.data.handleResponse

object OAuth {
    const val ENDPOINT = "https://accounts.google.com/o/oauth2/v2/auth" +
            ""
    suspend fun redirect(
        scope: Array<String>,
        state: String? = null,
        includeGrantedScopes: Boolean? = null,
        enableGranularConsent: Boolean? = null,
        loginHint: String? = null,
        prompt: Array<String>? = null
    ) {
        println(window.location.origin)
        window.location.href = buildURI(
            "https://accounts.google.com/o/oauth2/v2/auth",
            buildMap {
                put("client_id", Secrets.OAUTH_CLIENT_ID)
                put("redirect_uri", window.location.origin)
                put("response_type", "token")
                put("scope", scope.joinToString(" "))
                state?.let { put("state", it) }
                includeGrantedScopes?.let { put("include_granted_scopes", includeGrantedScopes.toString()) }
                enableGranularConsent?.let { put("enable_granular_consent", enableGranularConsent.toString()) }
                loginHint?.let { put("login_hint", loginHint.toString()) }
                prompt?.let { put("prompt", prompt.joinToString(" ")) }
            }
        )
    }
}