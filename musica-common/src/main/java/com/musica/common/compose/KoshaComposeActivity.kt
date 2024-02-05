package com.musica.common.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.CallSuper
import androidx.compose.runtime.Composable
import com.musica.common.compose.theme.KoshaTheme


/**
 * An activity whose content view is defined by a composable.
 * ```
 * class MyActivity : KoshaComposeActivity() {
 *
 *     @Composable @Exclude
 *     override fun ActivityContent() {
 *         MyComposable()
 *     }
 * }
 * ```
 */

abstract class KoshaComposeActivity : ComponentActivity() {

    @Composable
    @Exclude
    abstract fun ActivityContent()

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoshaTheme {
                ActivityContent()
            }
        }
    }
}