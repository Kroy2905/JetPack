package com.kroy.sseditor.models

data class copyContactReqBody(
    val clientId: Int,
    val contactList: List<ContactItem>,
    val dayName: String
)