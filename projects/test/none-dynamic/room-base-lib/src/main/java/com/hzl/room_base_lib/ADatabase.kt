package com.hzl.room_base_lib

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DataA::class],
    version = 1,
    exportSchema = false)
abstract class ADatabase: RoomDatabase() {

    abstract fun getDataADao(): DataADao

}