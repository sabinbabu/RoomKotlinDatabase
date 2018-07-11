package com.binwin.studio.roomkotlindatabase.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.binwin.studio.roomkotlindatabase.model.Data
import com.binwin.studio.roomkotlindatabase.database.DataBase
import com.binwin.studio.roomkotlindatabase.database.DbWorkerThread
import com.binwin.studio.roomkotlindatabase.R
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {
    private var mDb: DataBase? = null
    private lateinit var mDbWorkerThread: DbWorkerThread


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()
        mDb = DataBase.getInstance(this)

        save.setOnClickListener {
            val note = Data()
            note.name = name.text.toString()
            note.age = age.text.toString().toInt()
            val task = Runnable { mDb?.DataDao()?.insert(note) }
            mDbWorkerThread.postTask(task)
           Toast.makeText(this,"addded",Toast.LENGTH_SHORT).show()
            finish()
        }

    }

}




