package com.example.selfcare.viewmodels

import android.media.MediaPlayer
import android.os.CountDownTimer
import android.os.Vibrator
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel

class BreatheViewModel: ViewModel() {
    var isStarted by mutableStateOf(false)
    var isMusic by mutableStateOf(true)
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
        isMusic = true
        numberPicked = 1
    }

    fun printSetting(): String {
        return "start: $isStarted, music: $isMusic, duration: $numberPicked"
    }
}