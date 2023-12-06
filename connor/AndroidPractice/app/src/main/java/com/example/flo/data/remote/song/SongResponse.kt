package com.example.flo.data.remote.song

import com.google.gson.annotations.SerializedName

data class SongResponse (
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message:String,
    @SerializedName(value = "result") val result: FloChartResult
    )

data class FloChartResult (
    @SerializedName(value = "songs") val songs: ArrayList<FloChartSongs>
    )

data class FloChartSongs (
    @SerializedName(value = "songIdx") val songIdx: Int,
    @SerializedName(value = "albumIdx") val albumIdx:Int,
    @SerializedName(value = "singer") val singer:String,
    @SerializedName(value = "title") val title:String,
    @SerializedName(value = "coverImgUrl") val coverImgUrl: String
    )

