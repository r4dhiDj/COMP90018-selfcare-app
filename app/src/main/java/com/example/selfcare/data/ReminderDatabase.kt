package com.example.selfcare.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.selfcare.data.model.Reminder

/**
 * Database for the reminders
 */

@Database(entities = [Reminder::class], version = 1, exportSchema = false)
abstract class ReminderDatabase: RoomDatabase() {

    abstract fun reminderDao(): ReminderDao

}