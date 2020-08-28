package com.ss_eduhub.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ss_eduhub.data.local.model.LocalVideosItem

@Database(entities = [LocalVideosItem::class], version = 1)
abstract class SSEduhubAppDatabase : RoomDatabase() {

    abstract val getVideoDao: VideoDao

    companion object {
        private var instance: SSEduhubAppDatabase? = null

        /**
         * this method will create singleton instance of the local app database
         */
        @Synchronized
        fun getInstance(context: Context): SSEduhubAppDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    SSEduhubAppDatabase::class.java,
                    "ss_eduhub_app"
                )
                    .build()
            }
            return instance
        }
    }
}