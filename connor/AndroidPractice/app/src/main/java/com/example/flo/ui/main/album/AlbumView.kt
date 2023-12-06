package com.example.flo.ui.main.album

import com.example.flo.data.remote.album.AlbumSongsResult

interface AlbumView {
    fun onGetAlbumLoading()
    fun onGetAlbumSuccess(code: Int, result: AlbumSongsResult)
    fun onGetAlbumFailure(message: String)
}