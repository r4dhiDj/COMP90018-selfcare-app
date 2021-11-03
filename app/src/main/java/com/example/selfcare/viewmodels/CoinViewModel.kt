package com.example.selfcare.viewmodels


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.selfcare.AR_Activity


/**
 * COMP90018 - SelfCare
 * Used to hold state for the coins collected as part of [AR_Activity]
 */

class CoinViewModel: ViewModel() {
    var coinCount by mutableStateOf(0)


    fun coinIncrement() {
        coinCount++

    }

}