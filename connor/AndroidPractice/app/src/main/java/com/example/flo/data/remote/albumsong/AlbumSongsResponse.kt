package com.example.flo.data.remote.albumsong

import com.google.gson.annotations.SerializedName

data class AlbumSongsResponse (
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message:String,
    @SerializedName(value = "result") val result: ArrayList<Songs>
)

data class Songs (
    @SerializedName(value = "songIdx") val songIdx:Int,
    @SerializedName(value = "title") val title:String,
    @SerializedName(value = "singer") val singer:String,
    @SerializedName(value = "isTitleSong") val isTitleSong: String
)
