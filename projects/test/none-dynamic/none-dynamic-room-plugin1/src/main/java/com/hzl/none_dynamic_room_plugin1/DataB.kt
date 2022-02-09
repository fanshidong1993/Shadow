package com.hzl.none_dynamic_room_plugin1

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DataB (
    @PrimaryKey val id: Int,
    val name: String
        ) 