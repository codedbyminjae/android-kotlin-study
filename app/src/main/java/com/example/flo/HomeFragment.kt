package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var albumRVAdapter: AlbumRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // DB에서 앨범 리스트 가져오기
        val songDB = SongDatabase.getInstance(requireContext())!!
        val albumList = ArrayList(songDB.albumDao().getAlbums())

        // Adapter 생성
        albumRVAdapter = AlbumRVAdapter(albumList)

        // RecyclerView 연결
        binding.homeTodayMusicAlbumRv.apply {
            adapter = albumRVAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        // 클릭 이벤트 등록
        albumRVAdapter.setMyItemClickListener(object : AlbumRVAdapter.MyItemClickListener {
            override fun onItemClick(album: Album) {
                moveToAlbum(album.id)
            }

            override fun onRemoveAlbum(position: Int) {
                // 필요 시 DB에서도 삭제 가능
            }
        })

        // 배너 설정
        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))

        binding.homeBannerVp.apply {
            adapter = bannerAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }

        return binding.root
    }

    // AlbumFragment로 이동 (albumId 전달)
    private fun moveToAlbum(albumId: Int) {
        val fragment = AlbumFragment()
        fragment.arguments = Bundle().apply {
            putInt("albumId", albumId)
        }

        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, fragment)
            .commitAllowingStateLoss()
    }
}
