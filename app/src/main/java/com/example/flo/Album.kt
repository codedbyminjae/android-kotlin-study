package com.example.flo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "AlbumTable")
data class Album(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,

        var title: String = "",
        var singer: String = "",
        var isLike: Boolean = false,
        var coverImg: Int? = null
)