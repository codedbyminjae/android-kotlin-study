package com.example.flo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.flo.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var song: Song = Song()
    private val gson: Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ⭐ RoomDB 초기 데이터
        inputDummyAlbums()
        inputDummySongs()

        initBottomNavigation()

        binding.mainPlayerCl.setOnClickListener {
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", song.id)
            editor.apply()

            startActivity(Intent(this, SongActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()

        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 1)

        val songDB = SongDatabase.getInstance(this)!!

        // 기본값 songId==0 일 때 1번 곡 불러오기
        song = songDB.songDao().getSong(songId)

        Log.d("song ID", song.id.toString())
        setMiniPlayer(song)
    }

    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    true
                }

                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    true
                }

                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    true
                }

                else -> false
            }
        }
    }

    private fun setMiniPlayer(song: Song) {
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainMiniplayerProgressSb.progress =
            (song.second * 100000) / song.playTime
    }

    // ⭐ Album 더미데이터
    private fun inputDummyAlbums() {
        val db = SongDatabase.getInstance(this)!!
        val albumDao = db.albumDao()

        if (albumDao.getAlbums().isNotEmpty()) return

        albumDao.insert(Album(title="IU 5th Album", singer="아이유", coverImg=R.drawable.img_album_exp2))
        albumDao.insert(Album(title="BTS Hits", singer="방탄소년단", coverImg=R.drawable.img_album_exp))
        albumDao.insert(Album(title="AESPA Vol.1", singer="에스파", coverImg=R.drawable.img_album_exp3))
    }

    // Song 더미데이터 (albumIdx 연결됨)
    private fun inputDummySongs() {
        val db = SongDatabase.getInstance(this)!!
        val songDao = db.songDao()

        if (songDao.getSongs().isNotEmpty()) return

        // albumIdx = 1 → 아이유 앨범
        songDao.insert(Song("Lilac", "아이유", 0, 200, false, "music_lilac", R.drawable.img_album_exp2, false, 1))
        songDao.insert(Song("Flu", "아이유", 0, 200, false, "music_flu", R.drawable.img_album_exp2, false, 1))

        // albumIdx = 2 → BTS 앨범
        songDao.insert(Song("Butter", "방탄소년단", 0, 190, false, "music_butter", R.drawable.img_album_exp, false, 2))
        songDao.insert(Song("Boy with Luv", "방탄소년단", 0, 230, false, "music_boy", R.drawable.img_album_exp4, false, 2))

        // albumIdx = 3 → AESPA 앨범
        songDao.insert(Song("Next Level", "에스파", 0, 210, false, "music_next", R.drawable.img_album_exp3, false, 3))
    }
}
