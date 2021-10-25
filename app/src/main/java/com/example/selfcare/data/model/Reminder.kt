package com.example.selfcare.data.model


data class Reminder(
    val title: String,
    val time: String

)



val defaultReminders = listOf(
    Reminder("Go to bed ", "11:00"),
    Reminder("Water Plants", "5:00"),
    Reminder("Feed Cat", "7:00")
)