package xdevuikit.core.utils

/**
 * Extension function for accessing & modifying the first element of a generic [Array]
 */
var <T> Array<T>.first
    set(value) = set(0, value)
    get() = get(0)

/**
 * Extension function for accessing & modifying the second element of a generic [Array]
 */
var <T> Array<T>.second
    set(value) = set(1, value)
    get() = get(1)
/**
 * Extension function for accessing & modifying the first element of an [IntArray]
 */
var IntArray.first
    set(value) = set(0, value)
    get() = get(0)

/**
 * Extension function for accessing & modifying the second element of an [IntArray]
 */
var IntArray.second
    set(value) = set(1, value)
    get() = get(1)