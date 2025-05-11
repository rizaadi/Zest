package com.zephysus.data.local.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
class QuoteEntity(
    @PrimaryKey(autoGenerate = false) val quoteId: String,
    val title: String,
    val author: String,
    val createdAt: Long,
)
