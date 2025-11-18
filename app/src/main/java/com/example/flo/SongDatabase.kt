package com.example.flo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Song::class,
        Album::class,
        User::class,
        Like::class
    ],
    version = 1
)
abstract class SongDatabase : RoomDatabase() {

    abstract fun albumDao(): AlbumDao
    abstract fun songDao(): SongDao
    abstract fun userDao(): UserDao

    companion object {
        private var instance: SongDatabase? = null

        @Synchronized
        fun getInstance(context: Context): SongDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    SongDatabase::class.java,
                    "song-database"
                )
                    .fallbackToDestructiveMigration()   // ★ DB 버전 충돌 시 자동 초기화
                    .allowMainThreadQueries()           // 메인 스레드 DB 접근 허용
                    .build()
            }
            return instance
        }
    }
}
