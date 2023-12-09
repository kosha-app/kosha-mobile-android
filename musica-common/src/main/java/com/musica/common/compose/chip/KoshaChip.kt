package com.musica.common.compose.chip

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.musica.common.compose.theme.KoshaTheme
import com.musica.common.compose.theme.Secondary
import com.musica.common.compose.theme.Tertiary

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun KoshaChip(
    text: String,
    isSelected: Boolean,
    onGenderSelected: (String) -> Unit
) {
    Chip(
        modifier = Modifier.padding(4.dp),
        colors = ChipDefaults.chipColors(backgroundColor = if (isSelected) Color.Black else Tertiary),
        onClick = { onGenderSelected(text) },
        border = BorderStroke(1.dp, Secondary)
    ) {
        Text(
            text = text,
            color = Secondary,
            fontWeight = if (isSelected) FontWeight.Bold else null
        )
    }
}

@Composable
@Preview
private fun KoshaChipPreviewNotSelected(){
    KoshaTheme {
        KoshaChip(text = "Nazo", isSelected = false, onGenderSelected = {})
    }
}

@Composable
@Preview
private fun KoshaChipPreviewSelected(){
    KoshaTheme {
        KoshaChip(text = "Nazo", isSelected = true, onGenderSelected = {})
    }
}