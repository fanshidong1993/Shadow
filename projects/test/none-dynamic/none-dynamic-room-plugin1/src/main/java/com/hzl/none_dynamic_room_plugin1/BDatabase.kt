package com.hzl.none_dynamic_room_plugin1

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DataB::class],
    version = 1,
    exportSchema = false)
abstract class BDatabase: RoomDatabase() {

    abstract fun getDataADao(): DataBDao

}