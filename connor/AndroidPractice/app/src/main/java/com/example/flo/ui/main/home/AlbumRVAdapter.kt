package com.example.flo.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.data.entities.Album
import com.example.flo.databinding.ItemAlbumBinding

class AlbumRVAdapter(private val albumList: ArrayList<Album>): RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>() {

    interface MyItemClickListner{ //item clicklistner를 저장하기 위한 인터페이스
        fun onPlayClick(album: Album)
        fun onItemClick(album: Album)
    }

    private lateinit var mItemClickListner: MyItemClickListner //아래 받은 것을 내부에서 사용하기 위해 선언
    fun setMyItemClickLitner(itemClickListner: MyItemClickListner) { //외부에서의 itemClickListner를 받기 위한 함수
        mItemClickListner = itemClickListner
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder { //뷰 홀더 생성 함수
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding) //재활용하기 위해 item객체를 viewholder에게 던짐
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { //position값을 가지고 있으므로 보통 클릭이벤트는 여기서 작성한다.
        holder.bind(albumList[position]) //binding해주는 함수
        holder.binding.itemAlbumPlayImgIv.setOnClickListener {mItemClickListner.onPlayClick(albumList[position]) }
        holder.binding.itemAlbumCoverImgIv.setOnClickListener{mItemClickListner.onItemClick(albumList[position])}
    }

    override fun getItemCount(): Int = albumList.size //앨범리스트의 크기 반환

    inner class ViewHolder(val binding: ItemAlbumBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(album: Album){
            binding.itemAlbumCoverTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)
        }
    }

}