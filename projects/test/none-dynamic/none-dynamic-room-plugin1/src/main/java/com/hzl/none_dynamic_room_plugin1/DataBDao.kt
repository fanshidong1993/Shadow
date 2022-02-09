package com.hzl.none_dynamic_room_plugin1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface DataBDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(data: DataB)

}