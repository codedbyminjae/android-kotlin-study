package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentSongBinding

class SongFragment : Fragment() {

    private lateinit var binding: FragmentSongBinding
    private lateinit var songRVAdapter: SongRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSongBinding.inflate(inflater, container, false)

        // 1) AlbumVPAdapter에서 전달한 albumId 받기
        val albumId = arguments?.getInt("albumId") ?: 0

        // 2) DB 객체 생성
        val songDB = SongDatabase.getInstance(requireContext())!!

        // 3) 해당 앨범의 노래 리스트 가져오기
        val songList = ArrayList(songDB.songDao().getSongsInAlbum(albumId))

        // 4) songRVAdapter 생성
        songRVAdapter = SongRVAdapter(songList)

        binding.songContentRv.apply {
            adapter = songRVAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        // 5) 클릭 이벤트 → SongActivity 이동
        songRVAdapter.setMyItemClickListener(object : SongRVAdapter.MyItemClickListener {
            override fun onItemClick(song: Song) {
                moveToSongActivity(song.id)
            }

            override fun onRemoveAlbum(position: Int) {
                // 필요 시 구현
            }
        })

        return binding.root
    }

    private fun moveToSongActivity(songId: Int) {
        val intent = Intent(activity, SongActivity::class.java)
        intent.putExtra("songId", songId)
        startActivity(intent)
    }
}
