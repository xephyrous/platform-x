package org.xephyrous.apis

import kotlinx.browser.window
import org.xephyrous.js.decodeURIComponent
import org.xephyrous.js.encodeURIComponent

/**
 * Utility function to build a complete URL with query parameters.
 *
 * @param url The base URL to which parameters will be appended.
 * @param params A map of query parameters and their corresponding values.
 *
 * @return The constructed URL with encoded query parameters.
 */
fun buildURI(
    url: String,
    params: Map<String, String>
): String {
    val encodedParams = params.entries.joinToString("&") { (key, value) ->
        "${encodeURIComponent(key)}=${encodeURIComponent(value)}"
    }
    val separator = if (url.contains("?")) "&" else "?"
    return "$url$separator$encodedParams"
}

/**
 * Retrieves all URL parameters, including both query parameters and hash fragment parameters.
 *
 * @return A map of URL parameters and their corresponding values.
 */
fun getAllUrlParams(): Map<String, String> {
    val params = mutableMapOf<String, String>()

    // Handle query params (after ?)
    val query = window.location.search.removePrefix("?")
    query.split("&").forEach { pair ->
        val parts = pair.split("=")
        if (parts.size == 2) {
            val (key, value) = parts
            params[key] = decodeURIComponent(value)
        }
    }

    // Handle hash fragment params (after #)
    val fragment = window.location.hash.removePrefix("#")
    fragment.split("&").forEach { pair ->
        val parts = pair.split("=")
        if (parts.size == 2) {
            val (key, value) = parts
            params[key] = decodeURIComponent(value)
        }
    }

    return params
}
