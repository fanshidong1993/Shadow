package com.tencent.shadow.none_dynamic_room_plugin2

import android.app.Application
import com.hzl.room_base_lib.DataA

class AApp: Application() {
    override fun onCreate() {
        super.onCreate()
        ADatabaseObject.getBDatabase(this).getDataADao().save(DataA(6, "4"))
    }
}