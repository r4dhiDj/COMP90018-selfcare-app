package com.example.selfcare.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

data class Feature(
    val title: String,
    @DrawableRes val iconId: Int,
    val lightColour: Color,
    val mediumColor: Color,
    val darkColor: Color,
    val navController: NavController
    )
