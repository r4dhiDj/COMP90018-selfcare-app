package com.example.selfcare.presentation.reminders

import com.example.selfcare.data.model.Reminder

/**
 * This data class represents the view state for the Reminder Screen.
 * All of this data should be formatted in a way that the [ReminderScreen] can take this information
 * and display it
 */
data class ReminderViewState(
    val reminders: List<Reminder> = emptyList()
)