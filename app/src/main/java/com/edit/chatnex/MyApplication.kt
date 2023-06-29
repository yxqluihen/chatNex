package com.edit.chatnex

import android.app.Application
import android.support.multidex.MultiDex

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


class MyApplication: Application() {

    companion object{
        //全局应用上下文
        lateinit var instance: MyApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        Logger.addLogAdapter(AndroidLogAdapter())
        MultiDex.install(this);


    }
}