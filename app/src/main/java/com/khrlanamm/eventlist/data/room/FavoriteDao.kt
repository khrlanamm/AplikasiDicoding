package com.example.sched.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khrlanamm.eventlist.data.response.EventDetail

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(event: EventDetail)

    @Query("DELETE FROM events_fav WHERE id =:id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM events_fav")
    fun getAllFavorite(): LiveData<MutableList<EventDetail>>

    @Query("SELECT * FROM events_fav WHERE id =:id")
    fun checkFav(id: Int): LiveData<MutableList<EventDetail>>
}