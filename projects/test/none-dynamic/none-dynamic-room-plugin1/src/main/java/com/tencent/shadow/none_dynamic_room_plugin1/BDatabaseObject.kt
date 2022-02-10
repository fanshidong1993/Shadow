package com.tencent.shadow.none_dynamic_room_plugin1

import android.content.Context
import androidx.room.Room
import com.hzl.room_base_lib.BDatabase

object BDatabaseObject {
    fun getBDatabase(context: Context) = Room
        .databaseBuilder(context, BDatabase::class.java,"BDatabase.db")
        .allowMainThreadQueries()
        .build()
}