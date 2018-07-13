package com.binwin.studio.roomkotlindatabase.dao

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.binwin.studio.roomkotlindatabase.model.Data

@Dao
interface DataDao {

    @Query("SELECT * from userData")
    fun getAll(): List<Data>

    @Insert(onConflict = REPLACE)
    fun insert(weatherData: Data)

//    @Update(onConflict = REPLACE)
//    fun updateNote(repos: Data)

//    @Insert
//    fun insertNote(note: Data): Long


    @Query("DELETE from userData")
    fun deleteAll()

    @Delete
    fun deleteNote(note: Data)

}