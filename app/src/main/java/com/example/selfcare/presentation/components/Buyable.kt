package com.example.selfcare.presentation.components

import androidx.annotation.DrawableRes

data class Buyable(
    val name: String,
    val description: String,
    @DrawableRes val imageId: Int,
    val cost: Int
)
