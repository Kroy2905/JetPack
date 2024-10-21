package com.kroy.sseditor.models

import android.graphics.Bitmap

data class ChatItem(
    val name: String = "",
    val message: String = "",
    val date: String = "",
    val profileImage: Bitmap? = null ,
    val unreadCount: Int = 0
)