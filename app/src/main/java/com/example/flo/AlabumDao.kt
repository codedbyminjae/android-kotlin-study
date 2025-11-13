package com.example.flo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AlbumDao {

    // 앨범 추가
    @Insert
    fun insert(album: Album)

    // 전체 앨범 조회
    @Query("SELECT * FROM AlbumTable")
    fun getAlbums(): List<Album>

    // 특정 앨범 조회
    @Query("SELECT * FROM AlbumTable WHERE id = :id")
    fun getAlbum(id: Int): Album
}
