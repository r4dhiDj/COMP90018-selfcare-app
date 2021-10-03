package com.example.selfcare.data.model.repositories

import com.example.selfcare.data.ReminderDao
import com.example.selfcare.data.model.Reminder
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * [ReminderRepository] serves as model data for displaying reminders
 */

@ViewModelScoped
class ReminderRepository @Inject constructor(private val reminderDao: ReminderDao) {

    val getAllReminders: Flow<List<Reminder>> = reminderDao.getAllReminders()

    // Doesn't need suspend because Flow is asynchronous
    fun getReminder(reminderId: Int): Flow<Reminder> {
        return reminderDao.getReminder(reminderId = reminderId)
    }

    suspend fun addReminder(reminder: Reminder) {
        reminderDao.addReminder(reminder = reminder)
    }

    suspend fun updateReminder(reminder: Reminder) {
        reminderDao.updateReminder(reminder = reminder)
    }

    suspend fun deleteReminder(reminder: Reminder) {
        reminderDao.deleteReminder(reminder = reminder)
    }

    suspend fun deleteAllTasks(){
        reminderDao.deleteAllReminders()
    }


}