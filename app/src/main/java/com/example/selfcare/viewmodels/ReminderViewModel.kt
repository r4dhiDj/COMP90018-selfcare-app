package com.example.selfcare.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.ui.viewmodel.viewModel
import com.example.selfcare.data.model.Reminder
import com.example.selfcare.data.model.repositories.ReminderRepository
import com.example.selfcare.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
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

    // ViewModel Attributes
    private val _allReminders = MutableStateFlow<RequestState<List<Reminder>>>(RequestState.Idle)
    // Publicly exposed to our composables
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



}