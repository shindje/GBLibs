package com.example.gblibs.mvp.model.entity.room.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `RoomImageCached` (`url` TEXT NOT NULL, `path` TEXT NOT NULL, PRIMARY KEY(`url`))")
    }
}