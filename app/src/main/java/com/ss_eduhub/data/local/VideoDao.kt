package com.ss_eduhub.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ss_eduhub.data.local.model.LocalVideosItem

@Dao
interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(localVideosItem: LocalVideosItem): Long

    @Query("SELECT * FROM videos WHERE lessonId = :lessonId")
    suspend fun getLessonsVideoData(lessonId: Int): List<LocalVideosItem>
}