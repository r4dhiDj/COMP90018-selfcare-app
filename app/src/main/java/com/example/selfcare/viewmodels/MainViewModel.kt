package com.example.selfcare.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.selfcare.data.local.SettingsDataStoreImpl
import com.example.selfcare.data.model.repositories.UserRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsDataStoreImpl: SettingsDataStoreImpl,
    private val userRepository: UserRepository
) : ViewModel() {
    var firstTime: MutableState<Boolean> =  mutableStateOf(false)
    var darkModeState: MutableState<Boolean> = mutableStateOf(false)
    var notifModeState: MutableState<Boolean> = mutableStateOf(true)
    var displayName: MutableState<String> =  mutableStateOf("MATE")
    var email: MutableState<String> =  mutableStateOf("")
    var uid: MutableState<String> =  mutableStateOf("")
    var password: MutableState<String> =  mutableStateOf("")
    var coins: MutableState<Int> = mutableStateOf(0)
    var boughtItems: MutableState<Map<String, Boolean>> = mutableStateOf(mapOf())

    fun getDarkMode(){
        viewModelScope.launch(IO){
            darkModeState.value = settingsDataStoreImpl.isDarkMode.first()
        }
    }

    fun setDarkMode(isDarkMode: Boolean){
        viewModelScope.launch(IO){
            settingsDataStoreImpl.storeDarkMode(isDarkMode)
            darkModeState.value = isDarkMode
        }
    }

    fun getNotifMode(){
        viewModelScope.launch(IO){
            notifModeState.value = settingsDataStoreImpl.isNotifMode.first()
        }
    }

    fun setNotifMode(isNotifMode: Boolean){
        viewModelScope.launch(IO){
            settingsDataStoreImpl.storeNotifMode(isNotifMode)
            notifModeState.value = isNotifMode
        }
    }

    /**fun getUsername(){
        viewModelScope.launch(IO){
            username.value = settingsDataStoreImpl.username.first()
        }
    }

    fun setUsername(usernameNew: String){
        viewModelScope.launch(IO){
            settingsDataStoreImpl.storeUsername(usernameNew)
            username.value = usernameNew
        }
    }
    */

    fun getUsername(){
        viewModelScope.launch(IO){
            displayName.value = userRepository.getUserDisplayName()
        }
    }

    fun setUsername(usernameNew: String){
        Log.d("inside view model1", usernameNew)
        viewModelScope.launch(IO){
            userRepository.setUserDisplayName(usernameNew)
            displayName.value = usernameNew
            Log.d("inside view model2", displayName.value)
        }
    }

    fun getUserEmail(){
        viewModelScope.launch(IO){
            email.value = userRepository.getUserEmail()
        }
    }

    fun setUserEmail(newEmail: String){
        viewModelScope.launch(IO){
            userRepository.setUserEmail(newEmail)
            email.value = newEmail
        }
    }


   fun setUserPassword(newPassword:String){
       viewModelScope.launch(IO){
           userRepository.setUserPassword(newPassword)
           password.value = newPassword
       }
   }

    fun setCoins(value: Int) {
        viewModelScope.launch(IO) {
            coins.value = value
        }
    }

    fun getUserUid(){
        viewModelScope.launch(IO){
            Log.d("inside view model, uid", uid.value)
            uid.value = userRepository.getUserUID()
        }
    }


    fun deleteSettingsData(){
        viewModelScope.launch(IO){
            settingsDataStoreImpl.deleteSettingsData()
        }
    }

    fun signOut() {
        Firebase.auth.signOut()
    }

    fun deleteUser(){
        viewModelScope.launch(IO){
            userRepository.deleteUser()
        }
    }





}