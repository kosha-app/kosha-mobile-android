package com.musica.common.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
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
import com.musica.common.compose.theme.MusicaphoneTheme
import com.musica.common.compose.theme.Secondary

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String,
    onBackPressed: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(
            text = title,
            color = Secondary
        ) },
        navigationIcon = {
            if (onBackPressed != null){
                Image(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable(onClick = onBackPressed),
                    painter = painterResource(id = R.drawable.arrow_left),
                    contentDescription = "TopBar Navigation Icon",
                    colorFilter = ColorFilter.tint(Secondary)
                )
            }
        },
        backgroundColor = Color(0xFF353A40),
        actions = actions
    )
}

@Composable
@Preview
fun PreviewTopBar() {
    MusicaphoneTheme {
        TopBar(title = "Something", onBackPressed = {})
    }
}

@Composable
@Preview
fun PreviewTopBarWithActions() {
    MusicaphoneTheme {
        TopBar(title = "Something", onBackPressed = {}, actions = { Text(text = "hdfgjshyd", color = Color.White)})
    }
}