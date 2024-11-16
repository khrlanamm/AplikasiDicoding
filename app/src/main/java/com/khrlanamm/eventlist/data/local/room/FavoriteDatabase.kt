package com.khrlanamm.eventlist.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.khrlanamm.eventlist.data.local.entity.EventDetail

@Database(entities = [EventDetail::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteDatabase? = null

        @JvmStatic
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDatabase::class.java, "local_favorites_data"
                ).fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
    }
}