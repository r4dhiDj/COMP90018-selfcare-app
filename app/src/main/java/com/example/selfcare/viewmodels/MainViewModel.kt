package com.example.selfcare.viewmodels

import android.app.Application
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.selfcare.data.local.SettingsDataStoreImpl
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsDataStoreImpl: SettingsDataStoreImpl,
) : ViewModel() {
    var firstTime: MutableState<Boolean> =  mutableStateOf(false)
    var darkModeState: MutableState<Boolean> = mutableStateOf(false)
    var notifModeState: MutableState<Boolean> = mutableStateOf(true)
    var username: MutableState<String> = mutableStateOf("MATE")


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