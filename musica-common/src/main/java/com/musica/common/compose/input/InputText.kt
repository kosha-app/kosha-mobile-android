package com.musica.common.compose.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.musica.common.compose.Exclude
import com.musica.common.compose.theme.KoshaTheme
import com.musica.common.compose.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Exclude
fun InputText(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable @Exclude (() -> Unit)? = null,
    leadingIcon: @Composable @Exclude (() -> Unit)? = null,
) {

    Column(modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier
                .background(
                    color = Primary,
                    shape = RoundedCornerShape(26.dp)
                )
                .fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            shape = RoundedCornerShape(26.dp),
            placeholder = { Text(text = placeholder) },
            keyboardOptions = keyboardOptions,
            colors = TextFieldDefaults
                .outlinedTextFieldColors(
                    textColor = Color.White,
                    focusedBorderColor = Color.White,
                    cursorColor = Color.White
                ),
            visualTransformation = visualTransformation,
            trailingIcon = trailingIcon,
            leadingIcon = leadingIcon,
        )
    }



}

@Preview(showBackground = true)
@Composable
@Exclude
fun Preview() {
    KoshaTheme {
        InputText(
            value = "",
            onValueChange = {},
            placeholder = "Username",
            trailingIcon = {}
        )
    }
}