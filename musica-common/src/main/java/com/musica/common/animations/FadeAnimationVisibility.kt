package com.musica.common.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable

@Composable
fun SlideInAnimationVisibility(
    showContent: Boolean,
    sheetProgress: Float,
    content: @Composable() AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = showContent,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight * sheetProgress.toInt() },
            animationSpec = tween(durationMillis = 450)
        ),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(durationMillis = 400)
        ),
        content = content
    )
}
