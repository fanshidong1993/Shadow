package com.hzl.none_dynamic_room_plugin2

import android.app.Application
import java.lang.Appendable

class AApp: Application() {
    override fun onCreate() {
        super.onCreate()
        ADatabaseObject.getBDatabase(this).getDataADao().save(DataA(9,"9"))
    }
}