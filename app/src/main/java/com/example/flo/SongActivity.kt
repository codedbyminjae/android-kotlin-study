package com.example.flo

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding
    lateinit var timer: Timer
    private var mediaPlayer: MediaPlayer? = null

    private var gson: Gson = Gson()
    private lateinit var songDB: SongDatabase

    private val songs = arrayListOf<Song>()
    private var nowPos = 0
    private var albumId = 0   // 앨범 id 저장용

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ⭐ albumId & songId 받기
        albumId = intent.getIntExtra("albumId", 0)

        initPlayList()     // 앨범 기반 playlist 생성
        initSong()         // 현재 곡 위치 계산 후 UI 렌더링
        initClickListener()
    }

    override fun onPause() {
        super.onPause()

        val currentSong = songs[nowPos]

        currentSong.second =
            ((binding.songProgressSb.progress * currentSong.playTime) / 100) / 1000
        currentSong.isPlaying = false

        setPlayerStatus(false)

        // 저장
        val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
        editor.putInt("songId", currentSong.id)
        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    // ⭐ 앨범 기반 playlist 초기화
    private fun initPlayList() {
        songDB = SongDatabase.getInstance(this)!!

        val songId = intent.getIntExtra("songId", 0)
        albumId = intent.getIntExtra("albumId", 0)

        songs.clear()

        if (albumId == 0) {
            // ⭐ albumId 없으면 전체 리스트 사용 (MainActivity → SongActivity)
            songs.addAll(songDB.songDao().getSongs())
        } else {
            // ⭐ 앨범에서 클릭한 경우 해당 앨범의 노래만
            songs.addAll(songDB.songDao().getSongsInAlbum(albumId))
        }

        if (songs.isEmpty()) {
            Toast.makeText(this, "재생할 음악이 없습니다.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        nowPos = getPlayingSongPosition(songId)
    }


    private fun initClickListener() {
        binding.songDownIb.setOnClickListener { finish() }

        binding.songMiniplayerIv.setOnClickListener { setPlayerStatus(true) }

        binding.songPauseIv.setOnClickListener { setPlayerStatus(false) }

        binding.songNextIv.setOnClickListener { moveSong(+1) }

        binding.songPreviousIv.setOnClickListener { moveSong(-1) }

        binding.songLikeIv.setOnClickListener {
            setLike(songs[nowPos].isLike)
        }
    }

    private fun initSong() {

        // 1) 먼저 Intent로 전달된 값이 있으면 사용
        val intentSongId = intent.getIntExtra("songId", -1)

        // 2) SharedPreferences에 저장된 마지막 곡
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val prefSongId = spf.getInt("songId", -1)

        val finalSongId = when {
            intentSongId != -1 -> intentSongId
            prefSongId != -1 -> prefSongId
            else -> songs[0].id
        }

        nowPos = getPlayingSongPosition(finalSongId)

        startTimer()
        setPlayer(songs[nowPos])
    }


    private fun setLike(isLike: Boolean) {
        val newState = !isLike
        songs[nowPos].isLike = newState

        songDB.songDao().updateIsLikeById(newState, songs[nowPos].id)

        if (newState) binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        else binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
    }

    private fun moveSong(direct: Int) {
        if (nowPos + direct < 0) {
            Toast.makeText(this, "first song", Toast.LENGTH_SHORT).show()
            return
        }
        if (nowPos + direct >= songs.size) {
            Toast.makeText(this, "last song", Toast.LENGTH_SHORT).show()
            return
        }

        nowPos += direct

        timer.interrupt()
        startTimer()

        mediaPlayer?.release()
        mediaPlayer = null

        setPlayer(songs[nowPos])
    }

    private fun getPlayingSongPosition(songId: Int): Int {
        for (i in 0 until songs.size) {
            if (songs[i].id == songId) return i
        }
        return 0
    }

    private fun setPlayer(song: Song) {
        binding.songMusicTitleTv.text = song.title
        binding.songSingerNameTv.text = song.singer

        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)

        binding.songAlbumIv.setImageResource(song.coverImg ?: 0)

        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)

        val music = resources.getIdentifier(song.music, "raw", packageName)
        mediaPlayer = MediaPlayer.create(this, music)

        if (song.isLike) binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        else binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)

        setPlayerStatus(song.isPlaying)
    }

    private fun setPlayerStatus(isPlaying: Boolean) {
        songs[nowPos].isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if (isPlaying) {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
            mediaPlayer?.start()
        } else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            if (mediaPlayer?.isPlaying == true) mediaPlayer?.pause()
        }
    }

    private fun startTimer() {
        timer = Timer(songs[nowPos].playTime, songs[nowPos].isPlaying)
        timer.start()
    }

    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true) : Thread() {
        private var second: Int = 0
        private var mills: Float = 0f

        override fun run() {
            try {
                while (true) {
                    if (second >= playTime) break

                    if (isPlaying) {
                        sleep(50)
                        mills += 50

                        runOnUiThread {
                            binding.songProgressSb.progress = ((mills / playTime) * 100).toInt()
                        }

                        if (mills % 1000 == 0f) {
                            runOnUiThread {
                                binding.songStartTimeTv.text = String.format("%02d:%02d", second / 60, second % 60)
                            }
                            second++
                        }
                    }
                }

            } catch (e: InterruptedException) {
                Log.d("Song", "쓰레드 종료됨: ${e.message}")
            }
        }
    }
}
