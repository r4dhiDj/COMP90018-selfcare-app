package com.example.selfcare.presentation.components.screens

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

data class Feature(
    val title: String,
    @DrawableRes val iconId: Int,
    val lightColour: Color,
    val mediumColour: Color,
    val darkColour: Color,
    val navController: NavController
    )
