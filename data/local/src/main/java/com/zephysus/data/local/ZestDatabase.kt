package com.zephysus.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zephysus.data.local.dao.QuoteEntity
import com.zephysus.data.local.dao.QuotesDao

@Database(
    entities = [QuoteEntity::class], version = DatabaseMigrations.DB_VERSION
)
internal abstract class ZestDatabase : RoomDatabase() {
    abstract fun quotesDao(): QuotesDao
}