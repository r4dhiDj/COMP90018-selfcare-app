package com.example.selfcare.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val DarkThemeColors = darkColors(
    primary = Purple300,
    primaryVariant = Purple200,
    onPrimary = Color.Black,
    secondary = Purple300,
    secondaryVariant = Purple200,
    onSecondary = Color.Black,
    error = RedErrorDark,
    onError = Color.White,
    background = Grey2,
    onBackground = Color.White,
    surface = Grey3,
    onSurface = Color.White
)

private val LightThemeColors = lightColors(
    primary = Purple700,
    primaryVariant = Purple500,
    onPrimary = Color.Black,
    secondary = Purple700,
    onSecondary = Color.Black,
    error = RedErrorLight,
    onError = Color.White,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
)


private val DarkColorPalette = darkColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200
)



@Composable
fun SelfCareTheme(darkMode: Boolean, content: @Composable() () -> Unit) {
    val colors = if (darkMode) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}