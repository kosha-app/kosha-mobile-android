package com.musica.common.compose.button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.musica.common.compose.theme.Primary
import com.musica.common.compose.theme.Secondary

@Composable
fun MusicButton(
    modifier: Modifier = Modifier, buttonText: String, buttonColor: Color, width: Dp, onClick: () -> Unit
) {
    Column(modifier = modifier) {
        Button(
            modifier = Modifier
                .shadow(
                    elevation = 3.dp, spotColor = Secondary, shape = RoundedCornerShape(
                        32.dp
                    )
                )
                .width(width),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
        ) {
            Text(
                text = buttonText, color = Color.White
            )
        }
    }

}