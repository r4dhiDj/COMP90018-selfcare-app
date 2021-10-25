package com.example.selfcare.data.model.repositories

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class UserRepository {
    val user = Firebase.auth.currentUser


    fun getUserDisplayName(): String{
        var name: MutableState<String> = mutableStateOf("")
        user?.let{
            if (user.displayName!=null) {
                name.value = user.displayName!!
            }
        }
        return name.value
    }

    fun getUserEmail(): String{
        var email: MutableState<String> = mutableStateOf("")
        user?.let{
            email.value = user.email!!
        }
        return email.value
    }

    fun getUserUID(): String{
        var uid: MutableState<String> = mutableStateOf("")
        user?.let{
            uid.value = user.uid
            Log.d("inside get ", user.uid)
        }
        return uid.value
    }

    fun setUserDisplayName(newDisplayName: String){
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
        user!!.updateEmail(newEmail)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User email address updated.")
                }
            }
    }


    fun deleteUser() {
        user!!.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User account deleted.")
                }
            }
    }
}

