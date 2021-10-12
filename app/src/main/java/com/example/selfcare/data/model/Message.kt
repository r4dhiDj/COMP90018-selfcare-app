package com.example.selfcare.data.model

data class Message (
    val author: String,
    val body: String,
    val dateTime: String,
    val options: List<String>?
)

val defaultMessage = listOf(
    Message("Mascot", "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod " +
            "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
            "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo " +
            "consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse " +
            "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non " +
            "proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", "Mon 15:30", null),
    Message("Thomas", "I'm doing fine.", "Mon 15:31", null),
    Message("Mascot", "How do you feel?", "Mon 15:30", null),
    Message("Thomas", "I'm doing fine.", "Mon 15:31", null),
    Message("Mascot", "How do you feel?", "Mon 15:30", null),
    Message("Thomas", "I'm doing fine.", "Mon 15:31", null),
    Message("Mascot", "How do you feel?", "Mon 15:30", null),
    Message("Thomas", "I'm doing fine.", "Mon 15:31", null),
    Message("Mascot", "How do you feel?", "Mon 15:30", listOf("Great", "Fine", "Nah")),
)