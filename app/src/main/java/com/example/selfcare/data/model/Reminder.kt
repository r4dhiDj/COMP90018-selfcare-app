package com.example.selfcare.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.selfcare.constants.Constants.DATABASE_TABLE

/**
 * Data Class used to create a table in our database (ROOM)
 */

@Entity(tableName = DATABASE_TABLE)
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val time: String

)



//val defaultReminders = listOf(
//    Reminder("Go to bed ", "11:00"),
//    Reminder("Water Plants", "5:00"),
//    Reminder("Feed Cat", "7:00")
//)