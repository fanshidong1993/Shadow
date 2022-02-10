package com.tencent.shadow.test.none_dynamic.host

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface DataCDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(data: DataC)

}