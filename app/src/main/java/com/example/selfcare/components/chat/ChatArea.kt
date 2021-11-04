package com.example.selfcare.components.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items // <-- SHOULD INCLUDE THAT EXPLICITLY
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun ChatArea(
    chat: List<Message>,
    onMessageDelete: (message: Message) -> Unit,
    scrollState: LazyListState,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopStart
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            state = scrollState,
            contentPadding = rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.statusBars,
//            additionalTop = 90.dp
            )
        ) {
            items(chat) { message ->
                ChatMessage(
                    message = message.text,
                    message.isUser,
                    onClick = { onMessageDelete(message) }
                )
            }
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
//                .clickable(onClick = onClick)
                .padding(16.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(if (isUser) MaterialTheme.colors.primary else MaterialTheme.colors.secondary)
                .padding(18.dp, 14.dp),
            color = if (isUser) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSecondary,
            fontSize = 20.sp,
            textAlign = if (isUser) TextAlign.Start else TextAlign.End
        )
    }
}
