package com.example.selfcare.data.model

import com.google.firebase.database.IgnoreExtraProperties

/**
 * COMP90018 - SelfCare
 * [User] represents User Object for integration with [Firebase]
 */

@IgnoreExtraProperties
data class User(val email: String? = null, val coins: Int ? = 0) {

}
