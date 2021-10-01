package com.example.selfcare.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.ui.graphics.vectormath.min
import androidx.ui.viewmodel.viewModel
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
 * The [ReminderViewModel] takes in a [reminderRepository] to request data
 */

@HiltViewModel
class ReminderViewModel @Inject constructor (
    private val reminderRepository : ReminderRepository,
) : ViewModel() {

    // ViewModel Attributes to be observed
    val id: MutableState<Int> = mutableStateOf(0)
    val title: MutableState<String> = mutableStateOf("")
    val time: MutableState<String> = mutableStateOf("")

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
     * Adds a task to the database (repository)
     */
    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val reminder = Reminder(
                title = title.value,
                time = time.value
            )
            reminderRepository.addReminder(reminder = reminder)
        }
    }

    /**
     * Handles database actions
     */

    fun handleDatabaseActions(action: Action) {
        when (action) {
            Action.ADD -> {
                addTask()
            }
            Action.UPDATE -> {

            }
            Action.DELETE -> {

            }
            Action.DELETE_ALL -> {

            }
            Action.UNDO -> {

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
            title.value = reminder.title
            time.value = reminder.time
        } else {
            getCurrentTime()
            title.value = ""
            time.value = String.format("%02d:%02d", hour, minute)
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
        return title.value.isNotEmpty() && time.value.isNotEmpty()
    }




}