package com.example.flo.data.remote.albumsong

import android.util.Log
import com.example.flo.ui.main.album.song.AlbumSongView
import com.example.flo.utils.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumSongsService {
    private lateinit var albumSongView: AlbumSongView

    fun setAlbumView(albumSongView: AlbumSongView){
        this.albumSongView = albumSongView
    }

    fun getAlbumSongs(albumIdx: Int){
        val albumSongsService = getRetrofit().create(AlbumSongsRetrofitInterface::class.java)

        albumSongView.onGetAlbumLoading()

        albumSongsService.getAlbumSongs(albumIdx).enqueue(object : Callback<AlbumSongsResponse> {
            override fun onResponse(call: Call<AlbumSongsResponse>, response: Response<AlbumSongsResponse>) {
                if(response.isSuccessful && response.code() == 200) {
                    val albumSongsResponse: AlbumSongsResponse = response.body()!!

                    Log.d("ALBUMSONG-RESPONSE", albumSongsResponse.toString())

                    when(val code = albumSongsResponse.code) {
                        1000 -> {
                            albumSongView.onGetAlbumSuccess(code, albumSongsResponse.result!!)
                        }

                        else -> albumSongView.onGetAlbumFailure(albumSongsResponse.message)
                    }
                }
            }

            override fun onFailure(call: Call<AlbumSongsResponse>, t: Throwable) {
                albumSongView.onGetAlbumFailure("네트워크 오류가 발생했습니다.")
            }

        })
    }
}