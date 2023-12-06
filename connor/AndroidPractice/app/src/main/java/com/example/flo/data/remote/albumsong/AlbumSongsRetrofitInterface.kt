package com.example.flo.data.remote.albumsong

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumSongsRetrofitInterface {
    @GET("/albums/{albumIdx}") //앨범 수록곡
    fun getAlbumSongs(@Path("albumIdx") albumIdx: Int): Call<AlbumSongsResponse>
}