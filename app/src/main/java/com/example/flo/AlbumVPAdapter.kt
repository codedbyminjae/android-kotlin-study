package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import android.os.Bundle

class AlbumVPAdapter(fragment: Fragment, private val albumId: Int) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {

            // 수록곡 Fragment → albumId 전달 필수
            0 -> SongFragment().apply {
                arguments = Bundle().apply {
                    putInt("albumId", albumId)
                }
            }

            // 상세 정보 Fragment → albumId 전달 (선택적)
            1 -> DetailFragment().apply {
                arguments = Bundle().apply {
                    putInt("albumId", albumId)
                }
            }

            // 영상 Fragment → albumId 전달 (선택적)
            else -> VideoFragment().apply {
                arguments = Bundle().apply {
                    putInt("albumId", albumId)
                }
            }
        }
    }
}
