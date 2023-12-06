package com.example.flo.ui.main.look.chart

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flo.data.remote.song.FloChartResult
import com.example.flo.databinding.ItemChartBinding

class SongRVAdapter(val context: Context, val result: FloChartResult): RecyclerView.Adapter<SongRVAdapter.ViewHolder>()  {

    interface  MyItemClickListener{
        fun onRemoveSong(songId: Int)
    }
    private lateinit var myItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        myItemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemChartBinding = ItemChartBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.bind(result.songs[position])

        if(result.songs[position].coverImgUrl == "" || result.songs[position].coverImgUrl == null){

        }else{
            Log.d("image", result.songs[position].coverImgUrl)
            Glide.with(context).load(result.songs[position].coverImgUrl).into(holder.coverImg)
        }
        holder.title.text = result.songs[position].title
        holder.singer.text = result.songs[position].singer
    }

    override fun getItemCount(): Int = result.songs.size


    inner class ViewHolder(val binding: ItemChartBinding): RecyclerView.ViewHolder(binding.root){

        val coverImg : ImageView = binding.itemChartCoverImgIv
        val title : TextView = binding.itemChartTitleTv
        val singer : TextView = binding.itemChartSingerTv
    }
}