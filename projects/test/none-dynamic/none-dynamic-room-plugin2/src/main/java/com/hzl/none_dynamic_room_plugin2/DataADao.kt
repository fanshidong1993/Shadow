package com.hzl.none_dynamic_room_plugin2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DataADao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(data: DataA)

}