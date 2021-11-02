package com.example.selfcare.presentation.components

import androidx.annotation.DrawableRes
import com.example.selfcare.R
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Buyable(
    val name: String = "Item",
    val description: String = "",
    @DrawableRes val imageId: Int = R.drawable.ic_face,
    val cost: Int = 0
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "description" to description,
            "cost" to cost,
        )
    }
}
