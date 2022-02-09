package com.hzl.none_dynamic_room_plugin1

import android.app.Application
import java.lang.Appendable

class BApp: Application() {
    override fun onCreate() {
        super.onCreate()
        BDatabaseObject.getBDatabase(this).getDataADao().save(DataB(0,"0"))
    }
}