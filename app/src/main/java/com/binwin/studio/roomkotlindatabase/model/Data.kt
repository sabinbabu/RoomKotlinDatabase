package com.binwin.studio.roomkotlindatabase.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.ColumnInfo

@Entity(tableName = "userData")
data class Data(@PrimaryKey(autoGenerate = true) var id: Long?,
                @ColumnInfo(name = "name") var name: String,
                @ColumnInfo(name = "age") var age: Int

){
    constructor():this(null,
            "",0)
}

