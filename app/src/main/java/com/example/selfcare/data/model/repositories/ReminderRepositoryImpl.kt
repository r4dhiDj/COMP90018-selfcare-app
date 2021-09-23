package com.example.selfcare.data.model.repositories

import com.example.selfcare.data.model.Reminder
import com.example.selfcare.data.model.defaultReminders

/**
 * Implementation of the Reminder Repository
 */

class ReminderRepositoryImpl: ReminderRepository {

    override suspend fun fetchReminders(): List<Reminder> {
        return defaultReminders
    }

}