package com.example.flo.ui.main.locker.savedalbum

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.data.entities.Album
import com.example.flo.databinding.ItemLockerAlbumBinding

class AlbumLockerRVAdapter (): RecyclerView.Adapter<AlbumLockerRVAdapter.ViewHolder>() {
    private val albums = ArrayList<Album>()

    interface MyItemClickListner{
        fun onRemoveSong(songId: Int)
    }

    private lateinit var mItemClickListner: MyItemClickListner //아래 받은 것을 내부에서 사용하기 위해 선언
    fun setMyItemClickListener(itemClickListner: MyItemClickListner) { //외부에서의 itemClickListner를 받기 위한 함수
        mItemClickListner = itemClickListner
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder { //뷰 홀더 생성 함수
        val binding: ItemLockerAlbumBinding = ItemLockerAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding) //재활용하기 위해 item객체를 viewholder에게 던짐
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { //position값을 가지고 있으므로 보통 클릭이벤트는 여기서 작성한다.
        holder.bind(albums[position]) //binding해주는 함수
        holder.binding.itemSavedalbumMoreBtn.setOnClickListener {
            mItemClickListner.onRemoveSong(albums[position].id)
            removeSong(position)
        }
    }

    override fun getItemCount(): Int = albums.size //앨범리스트의 크기 반환

    @SuppressLint("NotifyDataSetChanged")
    fun addAlbums(albums: ArrayList<Album>) {
        this.albums.clear()
        this.albums.addAll(albums)

        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun removeSong(position: Int){
        albums.removeAt(position)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemLockerAlbumBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(album: Album){
            binding.itemSavedalbumTitleTv.text = album.title
            binding.itemSavedalbumSingerTv.text = album.singer
            binding.itemSavedalbumCoverImgIv.setImageResource(album.coverImg!!)
        }
    }

}