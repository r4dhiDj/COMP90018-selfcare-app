package com.example.selfcare.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.selfcare.data.model.Reminder
import com.example.selfcare.data.model.repositories.ReminderRepository
import com.example.selfcare.util.Action
import com.example.selfcare.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import javax.inject.Inject

/**
 * The [ReminderViewModel] used to hold state of the Current Reminder being accessed by the user
 * Also used to write to the local ROOM database via the [reminderRepository]
 */

@HiltViewModel
class ReminderViewModel @Inject constructor (
    private val reminderRepository : ReminderRepository,
) : ViewModel() {

    // ViewModel Attributes to be observed
    val id: MutableState<Int> = mutableStateOf(0)
    val title: MutableState<String> = mutableStateOf("")
    val time: MutableState<String> = mutableStateOf("")
    val text: MutableState<String> = mutableStateOf("")

    val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)

    /**
     * Obtain list of reminders
     */
    private val _allReminders = MutableStateFlow<RequestState<List<Reminder>>>(RequestState.Idle)
    val allReminders: StateFlow<RequestState<List<Reminder>>> = _allReminders

    // Obtains all reminders from the database
    fun getAllReminders() {
        _allReminders.value = RequestState.Loading
            try {
                viewModelScope.launch {
                    // Retrieves all reminders in our database
                    reminderRepository.getAllReminders.collect {
                        _allReminders.value = RequestState.Success(it)
                    }
                }
            } catch (e: Exception) {
                _allReminders.value = RequestState.Error(e)
            }


    }

    private val _selectedReminder: MutableStateFlow<Reminder?> = MutableStateFlow(null)
    val selectedReminder: StateFlow<Reminder?> = _selectedReminder

    // Gets a single reminder from the database
    fun getSelectedReminder(reminderId: Int) {
        viewModelScope.launch {
            reminderRepository.getReminder(reminderId = reminderId).collect { reminder ->
                _selectedReminder.value = reminder
            }
        }
    }

    /**
     * Adds a reminder to the database (repository)
     */
    private fun addReminder() {
        viewModelScope.launch(Dispatchers.IO) {
            val reminder = Reminder(
                title = title.value,
                time = time.value,
                text = text.value
            )
            reminderRepository.addReminder(reminder = reminder)
        }
    }

    /**
     * Updates a reminder to the database (repository)
     */
    private fun updateReminder() {
        viewModelScope.launch(Dispatchers.IO) {
            val reminder = Reminder(
                id = id.value,
                title = title.value,
                time = time.value,
                text = text.value
            )
            reminderRepository.updateReminder(reminder = reminder)
        }
    }

    /**
     * Deletes a reminder
     */
    private fun deleteReminder() {
        viewModelScope.launch(Dispatchers.IO) {
            val reminder = Reminder(
                id = id.value,
                title = title.value,
                time = time.value,
                text = text.value
            )

            reminderRepository.deleteReminder(reminder = reminder)

        }
    }

    /**
     * Deletes all reminders
     */
    private fun deleteAllReminders() {
        viewModelScope.launch(Dispatchers.IO) {

            Log.d("TEST", "deleteAllReminders")
            reminderRepository.deleteAllReminders()

        }
    }


    /**
     * Handles database actions
     */

    fun handleDatabaseActions(action: Action) {
        when (action) {
            Action.ADD -> {
                addReminder()
            }
            Action.UPDATED -> {
                updateReminder()
            }
            Action.DELETE -> {
                deleteReminder()
            }
            Action.DELETE_ALL -> {
                deleteAllReminders()
            }
            Action.UNDO -> {
                addReminder()
            } else -> {

            }
        }
        this.action.value = Action.NO_ACTION
    }



    /**
     * Time related methods, gets the current time from the system
     */
    var hour = 0
    var minute = 0

    fun getCurrentTime() {
        val currentTime = Calendar.getInstance()
        hour = currentTime.get(Calendar.HOUR)
        minute = currentTime.get(Calendar.MINUTE)

    }

    /**
     * Updates the reminder fields for a given reminder
     */

    fun updateReminderFields(reminder: Reminder?) {
        if (reminder != null ) {
            id.value = reminder.id
            title.value = reminder.title
            time.value = reminder.time
            text.value = reminder.text
        } else {
            getCurrentTime()
            id.value = 0
            title.value = ""
            time.value = String.format("%02d:%02d", hour, minute)
            text.value = ""
        }
    }

    /**
     * Validation functions
     */

    fun updateTitle(newTitle: String) {
        if (newTitle.length < 25) {
            title.value = newTitle
        }
    }

    fun validateFields(): Boolean {
        return title.value.isNotEmpty() && time.value.isNotEmpty() && text.value.isNotEmpty()
    }


    /**
     *  Sets an alarm
     */
    fun  setAlarm(
        context: Context,
        callback: (Long) -> (Unit)
    ) {

        val alarmTime = time.value
        val split = alarmTime.split(":")
        val alarmHour = Integer.parseInt(split[0])
        val alarmMinute = Integer.parseInt(split[1])

        Calendar.getInstance().apply {
            Log.d("SET ALARM:", "setAlarm: $alarmHour + $alarmMinute")
            this.set(Calendar.HOUR_OF_DAY, alarmHour)
            this.set(Calendar.MINUTE, alarmMinute)
            this.set(Calendar.SECOND, 0)
            callback(this.timeInMillis)
        }
        Toast.makeText(
            context,
            "Reminder Set: ${title.value}",
            Toast.LENGTH_SHORT
        ).show()

    }




}