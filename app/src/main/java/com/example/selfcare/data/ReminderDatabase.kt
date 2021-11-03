package com.example.selfcare.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.selfcare.data.model.Reminder

/**
 *
 * COMP90018 - SelfCare
 *  [ReminderDatabase] Database for the reminders
 *  Updated schema to version 2 post-migration
 */

@Database(entities = [Reminder::class], version = 2, exportSchema = false)
abstract class ReminderDatabase: RoomDatabase() {

    abstract fun reminderDao(): ReminderDao

}

