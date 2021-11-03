package com.example.selfcare.viewmodels


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel

/**
 * COMP90018 - SelfCare
 * Used to toggle settings of [BreatheScreen] and set the time according to the picker
 */

class BreatheViewModel: ViewModel() {
    var isStarted by mutableStateOf(false)
    var isMusic by mutableStateOf(false)
    var numberPicked by mutableStateOf(1 )

    fun toggleMusic() {
        isMusic = !isMusic
    }

    fun startBreathing() {
        isStarted = !isStarted
    }

    fun setPicked(minute: Int) {
        numberPicked = minute
    }

    fun resetToDefault() {
        isStarted = false
        isMusic = false
        numberPicked = 1
    }

    fun printSetting(): String {
        return "start: $isStarted, music: $isMusic, duration: $numberPicked"
    }
}