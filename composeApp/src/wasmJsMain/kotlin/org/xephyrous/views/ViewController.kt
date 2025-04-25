package org.xephyrous.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.xephyrous.data.ViewModel

enum class TransitionType {
    NONE,
    FADE
}

enum class Views {
    AnonymousHomepage,
    UserHomepage,
    AdminHomepage,
    About,
    Admin,
    Calendar,
    Contact,
    Courses,
    Event,
    Profile
}

class ViewController(
    viewModel: ViewModel
) {
    private val _viewModel: ViewModel by mutableStateOf(viewModel)
    private var _currentView by mutableStateOf<(@Composable () -> Unit)?>(null)
    private var _nextView by mutableStateOf<(@Composable () -> Unit)?>(null)
    private var _currentTransition by mutableStateOf(TransitionType.FADE)
    private var _isAnimating by mutableStateOf(false)
    private var _transition by mutableStateOf(false)
    private var _clearCache by mutableStateOf(true)
    private var _defaultView = Views.AnonymousHomepage
    private var _views = mapOf<Views, @Composable () -> Unit>(
        Views.AnonymousHomepage to { Homepage(this) },
        Views.UserHomepage to { Homepage(this) },
        Views.AdminHomepage to { Homepage(this) },
        Views.About to { About(this) },
        Views.Admin to { Admin(this) },
        Views.Calendar to { Calendar(this) },
        Views.Contact to { Contact(this) },
        Views.Courses to { Courses(this) },
        Views.Event to { Event(this) },
        Views.Profile to { Profile(this) }
    )

    private var _intermediateBlock: (suspend () -> Unit)? = null
    private var _permBlocks: ArrayList<(suspend () -> Unit)> = arrayListOf()

    // Serve default view
    init { loadView(_defaultView, clearSoundCache = false) }

    fun loadView(
        view: Views,
        transition: TransitionType = TransitionType.FADE,
        clearSoundCache: Boolean = false
    ) {
        _nextView = _views[view]
        _currentTransition = transition
        _transition = true
        _clearCache = clearSoundCache
    }

    @Composable
    fun showView() {
        if (_transition) {
            when (_currentTransition) {
                TransitionType.FADE -> fadeTransition()
                TransitionType.NONE -> {
                    _transition = false
                    _currentView = _nextView
                    _nextView = null

                    _currentView?.invoke()
                }
            }
            return
        }

        _intermediateBlock = null
        _currentView?.invoke()
    }

    @Composable
    private fun fadeTransition() {
        var visibleCurrentScene by remember { mutableStateOf(true) }
        var visibleNextScene by remember { mutableStateOf(false) }
        var isLoading by remember { mutableStateOf(false) }

        val progress by rememberInfiniteTransition().animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )

        // 👇 Show loading bar (this will be immediately visible when isLoading = true)
        AnimatedVisibility(
            visible = isLoading,
            enter = fadeIn(tween(300)),
            exit = fadeOut(tween(300))
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LinearProgressIndicator(
                    modifier = Modifier.width(150.dp),
                    color = Color.White,
                    backgroundColor = Color.DarkGray,
                    progress = progress
                )
            }
        }

        // Launch coroutine for transitions
        LaunchedEffect(Unit) {
            visibleCurrentScene = false
            delay(500)

            if (_intermediateBlock != null) {
                isLoading = true

                // 👇 Yield to Compose so it has time to show the progress bar
                delay(1) // Just enough time to trigger recomposition

                delay(500)

                _permBlocks.forEach { it.invoke() }
                _intermediateBlock!!.invoke()

                delay(1000)
                isLoading = false
            }

            _currentView = _nextView
            _nextView = null

            visibleNextScene = true
            delay(500)

            _isAnimating = false
            _transition = false
        }

        // Scene transitions
        AnimatedVisibility(
            visible = visibleCurrentScene,
            exit = fadeOut(tween(500))
        ) {
            _currentView?.invoke()
        }

        AnimatedVisibility(
            visible = visibleNextScene,
            enter = fadeIn(tween(500))
        ) {
            _currentView?.invoke()
        }
    }

    fun setIntermediate(permanent: Boolean = false, block: suspend () -> Unit = {}) {
        if (permanent) {
            _permBlocks.add(block)
            return
        }

        val previousBlock = _intermediateBlock

        _intermediateBlock = if (previousBlock != null) {
            { previousBlock.invoke(); block.invoke() }
        } else {
            block
        }
        return
    }
}
