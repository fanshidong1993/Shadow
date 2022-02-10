package com.tencent.shadow.none_dynamic_room_plugin1

import android.app.Application
import com.hzl.room_base_lib.DataB

class BApp: Application() {
    override fun onCreate() {
        super.onCreate()
        BDatabaseObject.getBDatabase(this).getDataADao().save(DataB(7, "0"))
    }
}