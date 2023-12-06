package com.example.flo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


//제목, 가수, 사진, 재생시간, 현재 재생시간, isPlaying

@Entity(tableName = "SongTable")
data class Song(
    val title : String = "",
    val singer  : String = "",
    var second : Int = 0,
    var playTime : Int = 0,
    var isPlaying : Boolean = true,
    var music : String = "",
    var img: Int = 0,
    var isLike: Boolean = false,
    var albumIdx: Int = 0,
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
