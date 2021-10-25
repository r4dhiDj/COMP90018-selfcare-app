package com.example.selfcare.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.selfcare.data.model.Reminder
import com.example.selfcare.data.model.repositories.ReminderRepository
import com.example.selfcare.presentation.reminders.ReminderViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * The [ReminderViewModel] takes in a [reminderRepository] to request data, and alters this data
 * into a [ReminderViewState] that can be exposed by [viewState]
 */

@HiltViewModel
class ReminderViewModel @Inject constructor (
    private val reminderRepository : ReminderRepository,
) : ViewModel() {

    // ViewModel attributes
    val reminders: MutableState<List<Reminder>> = mutableStateOf(listOf())
    var reminderText by mutableStateOf("")
    var reminderTime = ""

    // Time attributes
    var hour = 0
    var minute = 0

    // Saved Time attributes
    var savedHour = 0
    var savedMinute = 0


    init {

        println("VIEW-MODEL: $reminderRepository")

        viewModelScope.launch {
            reminders.value = reminderRepository.fetchReminders()
        }

    }

    /**
     * Updates the text variable in the ViewModel (holds upon configuration changes)
     */
    fun onTextChanged(reminderText: String) {
        this.reminderText = reminderText
    }

    /**
     * Updates and formats the time variable in the ViewModel
     */
    fun updateTime() {
        this.reminderTime = "$savedHour:$savedMinute"

    }

    /**
     * Gets the current time of the system
     */
    fun getCurrentTime() {
        val currentTime = Calendar.getInstance()
        hour = currentTime.get(Calendar.HOUR)
        minute = currentTime.get(Calendar.MINUTE)

    }

    /**
     * Creates a new reminder object based on the input given by the user.
     */
    fun createReminder() {
        updateTime()
       val newReminder = Reminder(reminderText, reminderTime)
        Log.d("REMINDER-VM", "CreateReminder: $newReminder")

        // TODO: Add this to the ViewModel list


        // TODO: pressing ok goes back to the reminder views


    }


}