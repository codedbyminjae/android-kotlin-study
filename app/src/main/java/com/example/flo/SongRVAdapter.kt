package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemSongBinding

class SongRVAdapter(private val songList: ArrayList<Song>) :
    RecyclerView.Adapter<SongRVAdapter.ViewHolder>() {

    // 클릭 이벤트 인터페이스
    interface MyItemClickListener {
        fun onItemClick(song: Song)
        fun onRemoveAlbum(position: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSongBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songList[position])

        // 아이템 전체 클릭 → SongActivity로 이동
        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(songList[position])
        }

        // 나중에 필요하면 개별 버튼에도 listener 달 수 있음
        // holder.binding.itemSongPlayIv.setOnClickListener { ... }
    }

    override fun getItemCount(): Int = songList.size

    inner class ViewHolder(val binding: ItemSongBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(song: Song) {
            binding.itemSongTitleTv.text = song.title
            binding.itemSongSingerTv.text = song.singer
            binding.itemSongImgIv.setImageResource(song.coverImg ?: 0)
        }
    }
}
