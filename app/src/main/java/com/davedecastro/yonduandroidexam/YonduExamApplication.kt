package com.davedecastro.yonduandroidexam

import android.app.Application
import com.davedecastro.yonduandroidexam.data.db.YonduExamDatabase

class YonduExamApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        YonduExamDatabase.init(this)
    }

}