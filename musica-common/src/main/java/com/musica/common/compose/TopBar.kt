package com.musica.common.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.musica.common.R
import com.musica.common.compose.theme.KoshaTheme
import com.musica.common.compose.theme.Secondary
import com.musica.common.compose.theme.White

@Composable
@Exclude
fun TopBar(
    modifier: Modifier = Modifier,
    title: String,
    onBackPressed: (() -> Unit)? = null,
    actions: @Composable @Exclude RowScope.() -> Unit = {},
    circleBackButton: Boolean = false
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Column() {
                Text(
                    text = title,
                    color = White
                )
            }
        },
        navigationIcon = {
            if (onBackPressed != null) {
                if (circleBackButton) {
                    RoundImage(
                        painter = painterResource(id = R.drawable.arrow_left),
                        imageSize = 20.dp,
                        circleSize = 44.dp
                    )
                } else {
                    Image(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable(onClick = onBackPressed)
                            .size(30.dp),
                        painter = painterResource(id = R.drawable.arrow_left),
                        contentDescription = "TopBar Navigation Icon",
                        colorFilter = ColorFilter.tint(Secondary)
                    )
                }
            }
        },
        backgroundColor = Color(0xFF353A40),
        actions = actions,
        elevation = 0.dp
    )
}


@Composable
@Exclude
@Preview
fun PreviewTopBar() {
    KoshaTheme {
        TopBar(title = "Something", onBackPressed = {})
    }
}

@Composable
@Exclude
@Preview
fun PreviewTopBarWithActions() {
    KoshaTheme {
        TopBar(
            title = "Something",
            onBackPressed = {},
            actions = { Text(text = "hdfgjshyd", color = Color.White) })
    }
}

@Composable
@Exclude
@Preview
fun PreviewTopBarWithCircleButton() {
    KoshaTheme {
        TopBar(
            title = "Something",
            onBackPressed = {},
            actions = { Text(text = "hdfgjshyd", color = Color.White) },
            circleBackButton = true
        )
    }
}