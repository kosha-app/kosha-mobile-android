package com.musica.common.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.musica.common.compose.Exclude
import com.musica.common.compose.theme.DarkGrey
import com.musica.common.compose.theme.Secondary

@Composable
@Exclude
fun SettingsHeader(
    modifier: Modifier = Modifier,
    heading: String
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .background(
                    color = DarkGrey,
                    shape = RoundedCornerShape(26.dp)
                )
                .fillMaxWidth()
                .height(42.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = heading,
                color = Secondary,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
@Exclude
@Preview
private fun SettingsHeaderPreview() {
    SettingsHeader(heading = "Other")
}