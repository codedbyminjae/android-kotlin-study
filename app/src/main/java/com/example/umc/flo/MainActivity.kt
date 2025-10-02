package com.example.umc.flo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.umc.R
import com.example.umc.databinding.ActivityFloMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFloMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFloMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()

        // 미니 플레이어 클릭 시 SongActivity 실행
        binding.mainPlayerCl.setOnClickListener {
            val song = Song(
                binding.mainMiniplayerTitleTv.text.toString(),
                binding.mainMiniplayerSingerTv.text.toString()
            )

            val intent = Intent(this, SongActivity::class.java).apply {
                putExtra("title", song.title)
                putExtra("singer", song.singer)
            }
            startActivity(intent)
        }
    }

    private fun initBottomNavigation() {
        // 앱 실행 시 기본 홈 프래그먼트 세팅
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commit()

        binding.mainBnv.setOnItemSelectedListener { item ->
            val transaction = supportFragmentManager.beginTransaction()

            when (item.itemId) {
                R.id.homeFragment -> transaction.replace(R.id.main_frm, HomeFragment())
                R.id.lookFragment -> transaction.replace(R.id.main_frm, LockFragment())
                R.id.searchFragment -> transaction.replace(R.id.main_frm, SearchFragment())
                R.id.lockerFragment -> transaction.replace(R.id.main_frm, LockerFragment())
                else -> return@setOnItemSelectedListener false
            }

            transaction.commit()
            true
        }
    }
}
