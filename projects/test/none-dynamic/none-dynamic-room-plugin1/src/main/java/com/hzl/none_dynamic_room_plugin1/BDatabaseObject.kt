package com.hzl.none_dynamic_room_plugin1

import android.content.Context
import androidx.room.Room

object BDatabaseObject {
    fun getBDatabase(context: Context) = Room
        .databaseBuilder(context, BDatabase::class.java,"BDatabase.db")
        .allowMainThreadQueries()
        .build()
}