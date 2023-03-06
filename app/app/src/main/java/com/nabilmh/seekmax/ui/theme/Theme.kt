package com.nabilmh.seekmax.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


private val LightColorPalette = lightColors(
    primary = BackgroundBrandColor,
    primaryVariant = BackgroundSecondaryColor,
    secondary = TextSecondaryColor,
    background = BackgroundSecondaryColor,
    surface = CardBackgroundColor,
    onPrimary = TextReversedColor,
    /* Other default colors to override


    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MyApplicationTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {


    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}