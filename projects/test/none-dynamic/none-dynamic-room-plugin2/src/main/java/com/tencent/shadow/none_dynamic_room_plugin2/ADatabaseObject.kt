package com.tencent.shadow.none_dynamic_room_plugin2

import android.content.Context
import androidx.room.Room
import com.hzl.room_base_lib.ADatabase


object ADatabaseObject {
    fun getBDatabase(context: Context) = Room
        .databaseBuilder(context, ADatabase::class.java,"ADatabase.db")
        .allowMainThreadQueries()
        .build()
}