package com.hzl.none_dynamic_room_plugin2

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DataA::class],
    version = 1,
    exportSchema = false)
abstract class ADatabase: RoomDatabase() {

    abstract fun getDataADao(): DataADao

}