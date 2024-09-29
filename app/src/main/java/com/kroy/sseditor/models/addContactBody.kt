package com.kroy.sseditor.models

data class addContactBody(
    val clientId: Int=0,
    val comment1: String="",
    val comment2: String="",
    val comment3: String="",
    val contactImage: String="",
    val contactName: String="",
    val dayName: String="",
    val uploadedImage: String=""
)