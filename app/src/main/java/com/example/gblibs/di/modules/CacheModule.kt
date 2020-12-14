package com.example.gblibs.di.modules

import androidx.room.Room
import com.example.gblibs.mvp.model.cache.IUsersCache
import com.example.gblibs.mvp.model.cache.RoomUsersCache
import com.example.gblibs.mvp.model.entity.room.db.Database
import com.example.gblibs.ui.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {
    @Singleton
    @Provides
    fun database(app: App): Database = Room.databaseBuilder(app, Database::class.java, Database.DB_NAME)
        .build()


    @Singleton
    @Provides
    fun usersCache(database: Database): IUsersCache = RoomUsersCache(database)
}