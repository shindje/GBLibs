package com.example.gblibs.mvp.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomImageCached(
    @PrimaryKey var url: String,
    var path: String
)