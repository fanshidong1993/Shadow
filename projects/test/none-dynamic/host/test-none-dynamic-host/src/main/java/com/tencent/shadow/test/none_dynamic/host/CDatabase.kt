package com.tencent.shadow.test.none_dynamic.host

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DataC::class],
    version = 1,
    exportSchema = false)
abstract class CDatabase: RoomDatabase() {

    abstract fun getDataCDao(): DataCDao

}