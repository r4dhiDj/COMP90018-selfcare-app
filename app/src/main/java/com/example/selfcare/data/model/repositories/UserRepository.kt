package com.example.selfcare.data.model.repositories

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

/**
 * COMP90018 - SelfCare
 * [UserRepository] functions used to obtain User Information such as [setUserDisplayName] and
 * [setUserEmail]
 */

class UserRepository {

    fun getUserDisplayName(): String{
        val user = Firebase.auth.currentUser
        val name: MutableState<String> = mutableStateOf("")
        user?.let{
            if (user.displayName!=null) {
                name.value = user.displayName!!
            }
        }
        return name.value
    }

    fun getUserEmail(): String{
        val user = Firebase.auth.currentUser
        val email: MutableState<String> = mutableStateOf("")
        user?.let{
            email.value = user.email!!
        }
        return email.value
    }

    fun getUserUID(): String{
        val user = Firebase.auth.currentUser
        val uid: MutableState<String> = mutableStateOf("")
        user?.let{
            uid.value = user.uid
            Log.d("inside get ", user.uid)
        }
        return uid.value
    }

    fun setUserDisplayName(newDisplayName: String){
        val user = Firebase.auth.currentUser
        Log.d("inside repo", newDisplayName)
        val profileUpdates = userProfileChangeRequest {
            displayName = newDisplayName
        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile updated.")
                }
            }
        Log.d("insiderepo2", getUserDisplayName())
    }

   fun setUserEmail(newEmail: String){
        val user = Firebase.auth.currentUser
        user!!.updateEmail(newEmail)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User email address updated.")
                }
            }
    }

    fun setUserPassword(newPassword: String){
        val user = Firebase.auth.currentUser
        user!!.updatePassword(newPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User password updated.")
                }
            }

    }


    fun deleteUser() {
        val database = Firebase.database("https://kotlin-self-care-default-rtdb.firebaseio.com/").reference
        val user = Firebase.auth.currentUser
        val userRef = database.child("users").child(user!!.uid).ref
        userRef.removeValue()

        user!!.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User account deleted.")
                }
            }
    }
}

