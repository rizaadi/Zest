package com.zephysus.core.model

data class Quote(
    val id: String,
    val title: String,
    val author: String,
    val isFeatured: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
)
