package com.tencent.shadow.test.none_dynamic.host

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DataC (
    @PrimaryKey val id: Int,
    val name: String
        ) 