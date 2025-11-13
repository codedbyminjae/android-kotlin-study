package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator

class AlbumFragment : Fragment() {

    private lateinit var binding: FragmentAlbumBinding
    private val information = arrayListOf("수록곡", "상세정보", "영상")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        // 1) HomeFragment에서 넘겨준 albumId 받기
        val albumId = arguments?.getInt("albumId") ?: 0

        // 2) DB에서 album 정보 불러오기
        val songDB = SongDatabase.getInstance(requireContext())!!
        val album = songDB.albumDao().getAlbum(albumId)

        // 3) 상단 UI 셋팅
        setInit(album)

        // 뒤로가기 버튼
        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitAllowingStateLoss()
        }

        // 4) ViewPager Adapter 설정 (albumId 전달)
        val albumAdapter = AlbumVPAdapter(this, albumId)
        binding.albumContentVp.adapter = albumAdapter

        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp) { tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }

    private fun setInit(album: Album) {
        binding.albumAlbumIv.setImageResource(album.coverImg ?: 0)
        binding.albumMusicTitleTv.text = album.title
        binding.albumSingerNameTv.text = album.singer
    }
}
