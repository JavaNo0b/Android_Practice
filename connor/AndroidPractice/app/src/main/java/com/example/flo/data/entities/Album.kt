package com.example.flo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

//제목, 가수, 사진
@Entity(tableName = "AlbumTable")
data class Album(
    @PrimaryKey(autoGenerate = false) var id: Int = 0,
    var title: String? = "",
    var singer: String? = "",
    var coverImg: Int? = null,
)