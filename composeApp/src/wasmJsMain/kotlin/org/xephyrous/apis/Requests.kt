package org.xephyrous.apis

import org.xephyrous.js.encodeURIComponent

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