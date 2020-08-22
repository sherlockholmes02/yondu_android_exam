package com.davedecastro.yonduandroidexam.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.davedecastro.yonduandroidexam.data.db.converters.ListConverter
import com.davedecastro.yonduandroidexam.data.db.daos.MovieDao
import com.davedecastro.yonduandroidexam.data.db.entities.Movie

@Database(
    version = 1,
    entities = [
        Movie::class
    ]
)
@TypeConverters(ListConverter::class)
abstract class YonduExamDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private lateinit var INSTANCE: YonduExamDatabase

        fun init(context: Context) {
            if (!::INSTANCE.isInitialized)
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    YonduExamDatabase::class.java,
                    "yondu_exam_database"
                ).build()
        }

        fun getInstance() = INSTANCE

    }

    abstract fun movieDao(): MovieDao
}