package com.tikhonov.chatapp

data class Message(
    var text: String,
    var isUser: Boolean
    )

val defaultMessage = listOf(
    Message("Hi how can i help you",false)
)