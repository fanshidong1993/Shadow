package com.tencent.shadow.test.none_dynamic.host

import android.content.Context
import androidx.room.Room

object CDatabaseObject {
    fun getCDatabase(context: Context) = Room
        .databaseBuilder(context, CDatabase::class.java,"CDatabase.db")
        .allowMainThreadQueries()
        .build()
}