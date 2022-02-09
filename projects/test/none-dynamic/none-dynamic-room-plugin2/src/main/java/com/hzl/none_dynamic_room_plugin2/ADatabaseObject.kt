package com.hzl.none_dynamic_room_plugin2

import android.content.Context
import androidx.room.Room

object ADatabaseObject {
    fun getBDatabase(context: Context) = Room
        .databaseBuilder(context, ADatabase::class.java,"ADatabase.db")
        .allowMainThreadQueries()
        .build()
}