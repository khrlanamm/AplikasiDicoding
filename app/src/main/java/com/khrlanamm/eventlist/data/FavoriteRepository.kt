package com.khrlanamm.eventlist.data

import android.content.Context
import androidx.lifecycle.map
import com.khrlanamm.eventlist.data.local.entity.EventDetail
import com.khrlanamm.eventlist.data.local.room.FavoriteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class FavoriteRepository private constructor(context: Context) {
    private val favoriteDao = FavoriteDatabase.getInstance(context.applicationContext).favoriteDao()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    suspend fun insert(event: EventDetail) {
        coroutineScope.launch {
            favoriteDao.insert(event)
        }
    }

    suspend fun delete(id: Int) {
        coroutineScope.launch {
            favoriteDao.delete(id)
        }
    }

    fun getAllFavorite() = favoriteDao.getAllFavorite()

    fun checkFav(id: Int) = favoriteDao.checkFav(id).map {
        it.isNotEmpty()
    }

    companion object {
        fun getInstance(context: Context) = FavoriteRepository(context)
    }
}