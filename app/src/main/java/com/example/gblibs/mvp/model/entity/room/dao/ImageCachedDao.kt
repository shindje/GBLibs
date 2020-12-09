package com.example.gblibs.mvp.model.entity.room.dao

import androidx.room.*
import com.example.gblibs.mvp.model.entity.room.RoomImageCached

@Dao
interface ImageCachedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: RoomImageCached)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg users: RoomImageCached)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<RoomImageCached>)

    @Update
    fun update(user: RoomImageCached)

    @Update
    fun update(vararg users: RoomImageCached)

    @Update
    fun update(users: List<RoomImageCached>)

    @Delete
    fun delete(user: RoomImageCached)

    @Delete
    fun delete(vararg users: RoomImageCached)

    @Delete
    fun delete(users: List<RoomImageCached>)

    @Query("SELECT * FROM RoomImageCached")
    fun getAll(): List<RoomImageCached>

    @Query("SELECT * FROM RoomImageCached WHERE url = :url LIMIT 1")
    fun findByUrl(url: String): RoomImageCached?
}