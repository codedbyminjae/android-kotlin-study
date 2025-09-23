package com.example.umc

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.umc.databinding.ActivityBottomNavTestBinding

class BottomNavTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 첫 화면: 홈 프래그먼트 표시
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, HomeFragment())
            .commit()

        // 바텀 네비게이션 아이템 선택 리스너
        binding.mainBnv.setOnItemSelectedListener { item: MenuItem ->
            val selectedFragment = when (item.itemId) {
                R.id.homeFragment -> HomeFragment()
                R.id.diaryFragment -> DiaryFragment()
                R.id.calendarFragment -> CalendarFragment()
                R.id.friendFragment -> FriendFragment()
                R.id.mypageFragment -> MypageFragment()
                else -> null
            }

            selectedFragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, it)
                    .commit()
            }

            true
        }
    }
}
