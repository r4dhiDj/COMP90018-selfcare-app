package com.example.selfcare.viewmodels

import android.app.Application
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.selfcare.data.local.SettingsDataStoreImpl
//import com.example.selfcare.data.model.repositories.AuthRepository
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
    //private val authRepository: AuthRepository
) : ViewModel() {
    var firstTime: MutableState<Boolean> =  mutableStateOf(false)
    var darkModeState: MutableState<Boolean> = mutableStateOf(false)
    var notifModeState: MutableState<Boolean> = mutableStateOf(true)
    var username: MutableState<String> = mutableStateOf("MATE")

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _signedIn = MutableLiveData(false)
    val signedIn: LiveData<Boolean> = _signedIn

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    fun removeError() {
        _error.value = null
    }




    fun getFirstTime() {
        viewModelScope.launch(IO) {
            firstTime.value = settingsDataStoreImpl.isFirstTime.first()
        }
    }

    fun setFirstTime(isFirstTime: Boolean){
        viewModelScope.launch(IO) {
            settingsDataStoreImpl.storeFirstTime(isFirstTime)
            firstTime.value = isFirstTime
        }
    }

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

    fun getUsername(){
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

    fun deleteSettingsData(){
        viewModelScope.launch(IO){
            settingsDataStoreImpl.deleteSettingsData()
        }
    }





}