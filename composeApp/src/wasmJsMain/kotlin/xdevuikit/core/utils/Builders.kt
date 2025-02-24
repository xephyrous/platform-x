package xdevuikit.core.utils

import androidx.compose.animation.core.Easing

fun durations(vararg values: Int?): Array<Int?> = Array(values.size) { i -> values[i] }
fun easings(vararg values: Easing?): Array<Easing?> = Array(values.size) { i -> values[i] }
