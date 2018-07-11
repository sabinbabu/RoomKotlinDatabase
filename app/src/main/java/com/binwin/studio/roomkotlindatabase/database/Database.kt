package com.binwin.studio.roomkotlindatabase.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.binwin.studio.roomkotlindatabase.dao.DataDao
import com.binwin.studio.roomkotlindatabase.model.Data

@Database(entities = arrayOf(Data::class), version = 1)
abstract class DataBase : RoomDatabase() {

    abstract fun DataDao(): DataDao

    companion object {
        private var INSTANCE: DataBase? = null

        fun getInstance(context: Context): DataBase? {
            if (INSTANCE == null) {
                synchronized(DataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            DataBase::class.java, "weather.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}