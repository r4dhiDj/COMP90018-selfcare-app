package com.example.selfcare.data.model.repositories

import com.example.selfcare.data.model.Reminder

/**
 * [ReminderRepository] serves as model data for displaying reminders
 */
interface ReminderRepository {

    suspend fun fetchReminders(): List<Reminder>

}