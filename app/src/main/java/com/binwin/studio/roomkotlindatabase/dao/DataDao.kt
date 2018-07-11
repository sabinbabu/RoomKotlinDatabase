package com.binwin.studio.roomkotlindatabase.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.binwin.studio.roomkotlindatabase.model.Data

@Dao
interface DataDao {

    @Query("SELECT * from userData")
    fun getAll(): List<Data>

    @Insert(onConflict = REPLACE)
    fun insert(weatherData: Data)


//    @Insert
//    fun insertNote(note: Data): Long


    @Query("DELETE from userData")
    fun deleteAll()

    @Delete
    fun deleteNote(note: Data)

    @Delete
    fun deleteNotes(vararg note: Data)
}