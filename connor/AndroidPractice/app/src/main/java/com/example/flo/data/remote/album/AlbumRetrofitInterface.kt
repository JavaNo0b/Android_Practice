package com.example.flo.data.remote.album

import retrofit2.Call
import retrofit2.http.GET

interface AlbumRetrofitInterface {
    @GET("/albums")
    fun getAlbums(): Call<AlbumResponse>
}