package com.example.flo.data.remote.album

import com.google.gson.annotations.SerializedName

data class AlbumResponse (
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message:String,
    @SerializedName(value = "result") val result: AlbumSongsResult
)
data class AlbumSongsResult (
    @SerializedName(value = "albums") val albums: ArrayList<AlbumSongs>
)

data class AlbumSongs (
    @SerializedName(value = "albumIdx") val albumIdx:Int,
    @SerializedName(value = "title") val title:String,
    @SerializedName(value = "singer") val singer:String,
    @SerializedName(value = "coverImgUrl") val coverImgUrl: String
)
