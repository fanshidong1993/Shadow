package com.hzl.none_dynamic_room_plugin2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DataA (
    @PrimaryKey val id: Int,
    val name: String
        ) 