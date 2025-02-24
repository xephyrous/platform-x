package xdevuikit.core.utils

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Tracks value changes and updates animation state for use with [animateStateWithCallback]
 *
 * @param targetValue The value to animate to
 */
class AnimatedStateDelegate<T>(
    private var targetValue: T,
) : ReadWriteProperty<Any?, State<T>> {

    private var lastValue: T? = null
    var finished = false

    override fun getValue(thisRef: Any?, property: KProperty<*>): State<T> {
        if (targetValue != lastValue) {
            lastValue = targetValue
            finished = false
        }

        return object : State<T> {
            override val value: T
                get() = targetValue
        }
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: State<T>) {
        if (targetValue == value.value) return
        targetValue = value.value
        finished = false
    }
}

@Suppress("UNCHECKED_CAST")
@Composable
fun <T> animateStateWithCallback(
    targetValue: T,
    animationSpec: AnimationSpec<T>,
    callback: () -> Unit
): State<T> {
    val delegate = remember(targetValue) {
        AnimatedStateDelegate(targetValue)
    }

    val state = when (targetValue) {
        is Dp -> animateDpAsState(targetValue, animationSpec as AnimationSpec<Dp>)
        is Int -> animateIntAsState(targetValue, animationSpec as AnimationSpec<Int>)
        is Float -> animateFloatAsState(targetValue, animationSpec as AnimationSpec<Float>)
        is Color -> animateColorAsState(targetValue, animationSpec as AnimationSpec<Color>)
        else -> throw IllegalArgumentException("Unsupported type")
    } as State<T>

    if (!delegate.finished && state.value == targetValue) {
        callback()
        delegate.finished = true
    }

    return state
}