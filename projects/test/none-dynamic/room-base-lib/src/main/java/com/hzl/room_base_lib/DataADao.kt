package com.hzl.room_base_lib

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface DataADao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(data: DataA)

}