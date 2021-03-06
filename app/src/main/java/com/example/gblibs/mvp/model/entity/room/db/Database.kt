package com.example.gblibs.mvp.model.entity.room.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gblibs.mvp.model.entity.room.RoomGithubRepository
import com.example.gblibs.mvp.model.entity.room.RoomGithubUser
import com.example.gblibs.mvp.model.entity.room.RoomImageCached
import com.example.gblibs.mvp.model.entity.room.dao.ImageCachedDao
import com.example.gblibs.mvp.model.entity.room.dao.RepositoryDao
import com.example.gblibs.mvp.model.entity.room.dao.UserDao

@androidx.room.Database(entities = [RoomGithubUser::class, RoomGithubRepository::class, RoomImageCached::class], version = 2)
abstract class Database : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val repositoryDao: RepositoryDao
    abstract val imageCachedDao: ImageCachedDao

    companion object {
        const val DB_NAME = "database.db"
        private var instance: Database? = null
        fun getInstance() = instance ?: throw RuntimeException("Database has not been created. Please call create(context)")

        fun create(context: Context?) {
            if (instance == null) {
                instance = Room.databaseBuilder(context!!, Database::class.java, DB_NAME)
                    .addMigrations(MIGRATION_1_2)
                    .build()
            }
        }
    }
}