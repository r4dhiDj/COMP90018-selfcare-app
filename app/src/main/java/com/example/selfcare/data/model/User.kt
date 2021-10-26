package com.example.selfcare.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(val email: String? = null, val coins: Int ? = 0) {

}
