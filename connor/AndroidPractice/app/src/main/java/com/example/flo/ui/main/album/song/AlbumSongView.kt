package com.example.flo.ui.main.album.song

import com.example.flo.data.remote.albumsong.Songs

interface AlbumSongView {
    fun onGetAlbumLoading()
    fun onGetAlbumSuccess(code: Int, result: ArrayList<Songs>)
    fun onGetAlbumFailure(message: String)
}