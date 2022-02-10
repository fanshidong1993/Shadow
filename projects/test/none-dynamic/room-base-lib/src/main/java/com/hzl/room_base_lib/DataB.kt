package com.hzl.room_base_lib

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DataB (
    @PrimaryKey val id: Int,
    val name: String
        ) 