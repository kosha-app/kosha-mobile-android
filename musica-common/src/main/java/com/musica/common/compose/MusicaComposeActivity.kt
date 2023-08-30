package com.musica.common.compose

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.CallSuper
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.musica.common.compose.theme.MusicaphoneTheme


/**
 * An activity whose content view is defined by a composable.
 * ```
 * class MyActivity : MusicaComposeActivity() {
 *
 *     @Composable
 *     override fun ActivityContent() {
 *         MyComposable()
 *     }
 * }
 * ```
 */

abstract class MusicaComposeActivity: ComponentActivity() {

    @Composable
    abstract fun ActivityContent()

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicaphoneTheme {
                ActivityContent()
            }
        }
    }
}