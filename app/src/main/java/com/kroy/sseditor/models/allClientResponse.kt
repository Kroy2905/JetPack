package com.kroy.sseditor.models

data class allClientResponse(
    val `data`: List<clientItem>? = emptyList(),
    val message: String = "",
    val status_code: Int = 0
)