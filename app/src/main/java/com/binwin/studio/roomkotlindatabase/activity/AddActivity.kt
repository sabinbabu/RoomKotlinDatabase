package com.binwin.studio.roomkotlindatabase.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.binwin.studio.roomkotlindatabase.ADD
import com.binwin.studio.roomkotlindatabase.EDIT
import com.binwin.studio.roomkotlindatabase.model.Data
import com.binwin.studio.roomkotlindatabase.database.DataBase
import com.binwin.studio.roomkotlindatabase.database.DbWorkerThread
import com.binwin.studio.roomkotlindatabase.R
import kotlinx.android.synthetic.main.activity_add.*
import android.widget.TextView



class AddActivity : AppCompatActivity() {
    private var mDb: DataBase? = null
    private lateinit var mDbWorkerThread: DbWorkerThread


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()
        mDb = DataBase.getInstance(this)
        val editName = intent.extras.getString("name")
        val editAge = intent.extras.getInt("age")
        val editId = intent.extras.getLong("position")

        val intent = getIntent().extras.get("source")
        when(intent){
            ADD -> {
                save.setOnClickListener {
                    val note = Data()
                    note.name = name.text.toString()
                    note.age = age.text.toString().toInt()
                    val task = Runnable { mDb?.DataDao()?.insert(note) }
                    mDbWorkerThread.postTask(task)
                    Toast.makeText(this,"added",Toast.LENGTH_SHORT).show()
                    finish()
                }

            }
            EDIT -> {
                save.text = "UPDATE"
                name.setText(editName, TextView.BufferType.EDITABLE)
                age.setText(""+editAge)
                save.setOnClickListener {
                    val note = Data()
                    note.id = editId
                    note.name = name.text.toString()
                    note.age = age.text.toString().toInt()

                    //learn how to use update command

                    val task = Runnable { mDb?.DataDao()?.insert(note) }
                    mDbWorkerThread.postTask(task)
                   // Toast.makeText(this,note.id.toString()+ " updated",Toast.LENGTH_SHORT).show()
                    finish()
                }

            }
        }


    }

}




