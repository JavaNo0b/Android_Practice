package com.example.flo.ui.main.locker.storage

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.data.entities.Song
import com.example.flo.databinding.ItemStorageBinding

class StorageRVAdapter(): RecyclerView.Adapter<StorageRVAdapter.ViewHolder>()  {
    private val songs = ArrayList<Song>()

    interface  MyItemClickListener{
        fun onRemoveSong(songId: Int)
    }
    private lateinit var myItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        myItemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemStorageBinding = ItemStorageBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songs[position])
        holder.binding.itemStorageMoreBtn.setOnClickListener {
            myItemClickListener.onRemoveSong(songs[position].id)
            removeSong(position)
        }
    }

    override fun getItemCount(): Int = songs.size

    @SuppressLint("NotifyDataSetChanged")
    fun addSongs(songs:ArrayList<Song>){
        this.songs.clear()
        this.songs.addAll(songs)

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeSong(position: Int){
        songs.removeAt(position)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemStorageBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(song: Song){
            binding.itemStorageTitleTv.text = song.title
            binding.itemStorageSingerTv.text = song.singer
            binding.itemStorageCoverImgIv.setImageResource(song.img!!)
        }
    }
}