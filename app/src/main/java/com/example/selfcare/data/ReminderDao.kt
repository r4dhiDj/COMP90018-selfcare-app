package com.example.selfcare.data

import androidx.room.*
import com.example.selfcare.data.model.Reminder
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object - Define all SQL queries used with our database table.
 */

@Dao
interface ReminderDao {

    @Query("SELECT * FROM reminder_table ORDER BY id ASC")
    fun getAllReminders(): Flow<List<Reminder>>

    @Query("SELECT * FROM reminder_table WHERE id=:reminderId")
    fun getReminder(reminderId: Int): Flow<Reminder>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addReminder(reminder: Reminder)

    @Update
    suspend fun updateReminder(reminder: Reminder)

    @Delete
    suspend fun deleteReminder(reminder: Reminder)

    @Query("DELETE FROM reminder_table")
    suspend fun deleteAllReminders()

}