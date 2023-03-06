package com.nabilmh.seekmax.ui.theme

import androidx.compose.ui.graphics.Color

val ButtonColor = Color(0xFF2765CF)
val TextReversedColor = Color(0xFFFFFFFF)
val TextSecondaryColor = Color(0xFF69768C)
val TextPrimaryColor = Color(0xFF333A49)
val CardBackgroundColor = Color(0xFFFFFFFF)
val BackgroundSecondaryColor = Color(0xFFEFF3FB)
val BackgroundBrandColor = Color(0xFF0D3880)

fun getColor(colorString: String): Color {
    return Color(android.graphics.Color.parseColor(colorString))
}