package com.kroy.sseditor.models

data class ChatMessage(
    val message: String,
    val timestamp: String,
    val isSender: Boolean,
    val isLastMessage:Boolean = false
)