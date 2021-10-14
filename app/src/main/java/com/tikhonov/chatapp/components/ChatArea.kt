package com.tikhonov.chatapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items // <-- SHOULD INCLUDE THAT EXPLICITLY
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tikhonov.chatapp.Message
import com.tikhonov.chatapp.ui.theme.ChatAppTheme

@Composable
fun ChatArea(
    chat: List<Message>,
    onMessageDelete: (message: Message) -> Unit
) {
    Text(
        text = "CHAT",
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(chat) { message ->
            ChatMessage(
                message = message.text,
                message.isUser,
                onClick = { onMessageDelete(message) }
            )
        }
    }
}

@Composable
fun ChatMessage(
    message: String,
    isUser: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isUser) Alignment.Start else Alignment.End
    ) {
        Text(
            text = message,
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(16.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(if (isUser) MaterialTheme.colors.primary else MaterialTheme.colors.secondary)
                .padding(8.dp),
            color = if (isUser) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSecondary,
            fontSize = 24.sp,
            textAlign = if (isUser) TextAlign.Start else TextAlign.End
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FirstUserPreview() {
ChatAppTheme() {
    ChatMessage(message = "Hello!", isUser = true, onClick = {})
}
}

@Preview(showBackground = true)
@Composable
fun SecondUserPreview() {
    ChatAppTheme() {
        ChatMessage(message = "What's Up?", isUser = false, onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ChatPreview() {
    val chat = listOf(
        Message("Hello!", true),
        Message("What's Up?", false)
    )
    ChatAppTheme() {
        ChatArea(chat, onMessageDelete = {})
    }
}

