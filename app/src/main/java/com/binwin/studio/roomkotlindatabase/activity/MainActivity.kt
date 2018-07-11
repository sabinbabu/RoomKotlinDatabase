package com.binwin.studio.roomkotlindatabase.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.binwin.studio.roomkotlindatabase.*
import com.binwin.studio.roomkotlindatabase.adapter.RecyclerAdapter
import com.binwin.studio.roomkotlindatabase.database.DataBase
import com.binwin.studio.roomkotlindatabase.database.DbWorkerThread
import com.binwin.studio.roomkotlindatabase.model.Data
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() , RecyclerAdapter.OnDeleteClick {

    private var mDb: DataBase? = null
    private lateinit var mDbWorkerThread: DbWorkerThread
    private val mUiHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()
        mDb = DataBase.getInstance(this)

        fetchDataFromDb()
        add_btn.setOnClickListener { startActivity(Intent(this, AddActivity::class.java)) }
    }

    override fun onDeleteClick(items:List<Data>, pos: Int) {
        val task = Runnable {mDb?.DataDao()?.deleteNote(items[pos]) }
        mDbWorkerThread.postTask(task)
        fetchDataFromDb()
        Toast.makeText(this,"Item deleted",Toast.LENGTH_SHORT).show()
    }


    private fun bindDataWithUi(weatherData: List<Data>) {
        val mAdapter = RecyclerAdapter(this, weatherData)
        val mLayoutManager = LinearLayoutManager(this)
        recycler.layoutManager = mLayoutManager
        recycler.adapter = mAdapter
            }

    private fun fetchDataFromDb() {
        val task = Runnable {
            val weatherData =
                    mDb?.DataDao()?.getAll()
            mUiHandler.post {
                if (weatherData == null || weatherData?.size == 0) {
                    showToast("No data in cache..!!", Toast.LENGTH_SHORT)
                } else bindDataWithUi(weatherData = weatherData.toList())
            }
        }
        mDbWorkerThread.postTask(task)
    }

    private fun showToast(message: String,duration:Int){
        Toast.makeText(this,message,duration).show()
    }

    override fun onDestroy() {
        DataBase.destroyInstance()
        mDbWorkerThread.quit()
        super.onDestroy()
    }

    public override fun onResume() {
        fetchDataFromDb()
        super.onResume()
    }
}
