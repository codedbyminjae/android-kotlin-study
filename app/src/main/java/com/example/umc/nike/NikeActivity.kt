package com.example.umc.nike

import com.example.umc.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.umc.databinding.ActivityNikeBinding

class NikeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNikeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNikeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 최초 진입 시 NikeHomeFragment 보여줌
        openFragment(NikeHomeFragment())

        // 바텀 네비게이션 탭 클릭 시 프래그먼트 전환
        binding.nikeBottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    openFragment(NikeHomeFragment())
                    true
                }
                R.id.menu_shop -> {
                    openFragment(NikeShopFragment())
                    true
                }
                R.id.menu_wishlist -> {
                    openFragment(NikeWishlistFragment())
                    true
                }
                R.id.menu_cart -> {
                    openFragment(NikeCartFragment())
                    true
                }
                R.id.menu_profile -> {
                    openFragment(NikeProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    // 공통 프래그먼트 전환 함수
    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nike_nav_host, fragment)
            .commit()
    }
}
