package com.example.selfcare.presentation.components

data class Message(
    var text: String,
    var isUser: Boolean
    )

val defaultMessage = listOf(
    Message("Hi how can i help you",false)
)