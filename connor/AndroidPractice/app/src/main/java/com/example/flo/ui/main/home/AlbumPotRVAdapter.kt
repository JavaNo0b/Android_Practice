package com.example.flo.ui.main.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flo.data.remote.album.AlbumSongs
import com.example.flo.data.remote.album.AlbumSongsResult
import com.example.flo.databinding.ItemPotAlbumBinding

class AlbumPotRVAdapter(val context: Context, val result: AlbumSongsResult): RecyclerView.Adapter<AlbumPotRVAdapter.ViewHolder>()  {

    interface  MyItemClickListener{
        fun onItemClick(album: AlbumSongs)
    }
    private lateinit var myItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        myItemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemPotAlbumBinding = ItemPotAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(result.songs[position])
        holder.binding.itemAlbumCoverImgIv.setOnClickListener{myItemClickListener.onItemClick(result.albums[position])}

        if(result.albums[position].coverImgUrl == "" || result.albums[position].coverImgUrl == null){

        }else{
            Log.d("image", result.albums[position].coverImgUrl)
            Glide.with(context).load(result.albums[position].coverImgUrl).into(holder.coverImg)
        }
        holder.title.text = result.albums[position].title
        holder.singer.text = result.albums[position].singer
    }

    override fun getItemCount(): Int = result.albums.size


    inner class ViewHolder(val binding: ItemPotAlbumBinding): RecyclerView.ViewHolder(binding.root){

        val coverImg : ImageView = binding.itemAlbumCoverImgIv
        val title : TextView = binding.itemAlbumCoverTitleTv
        val singer : TextView = binding.itemAlbumSingerTv
    }
}