package com.example.selfcare.viewmodels


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import com.example.selfcare.AR_Activity
import kotlin.math.absoluteValue

class CoinViewModel: ViewModel() {
    var coinCount by mutableStateOf(0)


    fun coinIncrement() {
        coinCount++

    }

}