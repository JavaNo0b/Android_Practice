package com.example.flo.ui.main.album.song

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.data.remote.albumsong.Songs
import com.example.flo.databinding.ItemAlbumSongsBinding

class AlbumSongsRVAdapter(val context: Context, val result: ArrayList<Songs>): RecyclerView.Adapter<AlbumSongsRVAdapter.ViewHolder>() {

    interface MyItemClickListener {
    }

    private lateinit var myItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        myItemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: ItemAlbumSongsBinding =
            ItemAlbumSongsBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.bind(result.songs[position])

        if (result[position].isTitleSong == "F" || result[position].isTitleSong == null) {

        } else {
            Log.d("isTitleSong", result[position].isTitleSong)
            holder.binding.albumSongAlbumTitleTv.visibility = View.VISIBLE
        }
        holder.songIdx.text = (position+1).toString()
        holder.title.text = result[position].title
        holder.singer.text = result[position].singer
    }

    override fun getItemCount(): Int = result.size


    inner class ViewHolder(val binding: ItemAlbumSongsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val songIdx: TextView = binding.albumSongOrderTv
        val title: TextView = binding.albumListAlbumTitleTv
        val singer: TextView = binding.albumListSingerTv
    }
}
