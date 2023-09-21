package com.musica.common.settings.ui

import androidx.compose.runtime.Composable

data class SettingsItems(
    val heading: String,
    val content: @Composable () -> Unit,
)

fun settingsItems(logOutItems: List<SettingsItemsOptions>) = listOf(
    SettingsItems(
        heading = "Other",
        content = {
            logOutItems.forEach {
                SettingsItemView(
                    header = it.heading,
                    description = it.description,
                    onOptionClick = it.onClick
                )
            }
        },
    )
)

data class SettingsItemsOptions(
    val heading: String,
    val description: String,
    val onClick: () -> Unit,
    val trailing: @Composable () -> Unit = {}
)

//private val logOutItems = listOf(
//    SettingsItemsOptions(
//        heading = "Logout",
//        description = "This will log your device out",
//        onClick = { println("Sage: Logout11111 was clicked") }
//    ),
//    SettingsItemsOptions(
//        heading = "Logout",
//        description = "This will log your device out",
//        onClick = {println("Sage: Logout22222 was clicked")}
//    ),
//    SettingsItemsOptions(
//        heading = "Logout",
//        description = "This will log your device out",
//        onClick = {println("Sage: Logout3333333 was clicked")}
//    ),
//)