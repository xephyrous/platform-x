package org.xephyrous.data

import androidx.compose.runtime.Composable

/**
 * A representation of various UI state changes
 */
enum class Callback {
    VALUE_CHANGE
}

/**
 * Maps an enum value to a [Composable] lambda
 * @see CallbackStore
 */
data class EnumLambda(val enum: Enum<*>, val lambda: (Any) -> Unit)

/**
 * Stores any number of mappings from enum entry to lambda,
 * used for templating UI components
 *
 * @param callbacks Any number of [EnumLambda] entries to store
 */
class CallbackStore(private vararg val callbacks: EnumLambda) {
    private val _emptyLambda: () -> Unit = {}
    private val _callbacks: Map<Enum<*>, (Any) -> Unit> = callbacks.associateBy({ it.enum }, { it.lambda })

    /**
     * Checks for a given enum value in the callback store
     *
     * @param target The target enum (a member of [Callback]) to check for
     *
     * @return Whether the target enum value was found in the callback store
     */
    fun contains(target: Enum<*>) : Boolean {
        return _callbacks.containsKey(target)
    }

    /**
     * Checks if a given callback entry is an empty lambda or not
     *
     * @param target The target enum (a member of [Callback]) to check for
     *
     * @return Whether the target lambda exists and is empty (equal to [_emptyLambda]])
     */
    fun empty(target: Enum<*>) : Boolean {
        return _callbacks.containsKey(target) && _callbacks[target] == _emptyLambda
    }

    /**
     * Invokes a target enum in the callback store
     *
     * @param target The target enum (a member of [Callback]) to invoke
     */
    fun invoke(target: Enum<*>, param: Any = Unit) {
        try {
            _callbacks[target]?.invoke(param)
        } catch (e: Exception) {
            throw Exception("Invalid key '${target.name}' in callback store!")
        }
    }

    /**
     * Invokes a target enum in the callback store without throwing an error
     *
     * @param target The target enum (a member of [Callback]) to check for
     *
     * @return Whether the invocation of the target callback was successful
     */
    fun softInvoke(target: Enum<*>, param: Any = Unit) : Boolean {
        if (_callbacks.containsKey(target)) {
            try {
                _callbacks[target]?.invoke(param)
            } catch (_: Exception) {
                return false;
            }
            return true;
        }

        return false;
    }
}
