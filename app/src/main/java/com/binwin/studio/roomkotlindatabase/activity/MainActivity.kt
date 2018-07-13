package com.binwin.studio.roomkotlindatabase.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.binwin.studio.roomkotlindatabase.*
import com.binwin.studio.roomkotlindatabase.adapter.RecyclerAdapter
import com.binwin.studio.roomkotlindatabase.database.DataBase
import com.binwin.studio.roomkotlindatabase.database.DbWorkerThread
import com.binwin.studio.roomkotlindatabase.model.Data
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() , RecyclerAdapter.OnDeleteClick,RecyclerAdapter.OnEditClick {


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
        add_btn.setOnClickListener {  val intent =Intent(this, AddActivity::class.java)
            intent.putExtra("source",ADD)
            startActivity(intent) }
    }

    override fun onDeleteClick(items:List<Data>, pos: Int) {
        val task = Runnable {mDb?.DataDao()?.deleteNote(items[pos]) }
        mDbWorkerThread.postTask(task)
        fetchDataFromDb()
        Toast.makeText(this,"Item deleted",Toast.LENGTH_SHORT).show()
    }

    override fun onEditClick(items: List<Data>, pos: Int) {
        val intent =Intent(this, AddActivity::class.java)
        intent.putExtra("source",EDIT)
        intent.putExtra("position",items[pos].id)
        intent.putExtra("name",items[pos].name)
        intent.putExtra("age",items[pos].age)
        startActivity(intent)
//        val task = Runnable {mDb?.DataDao()?.updateNote(items[pos])}
//        mDbWorkerThread.postTask(task)
//        fetchDataFromDb()
      //  Toast.makeText(this,items[pos].id.toString()+" edited",Toast.LENGTH_SHORT).show()
    }


    private fun bindDataWithUi(weatherData: List<Data>) {
        val mAdapter = RecyclerAdapter(this,this, weatherData)
        val mLayoutManager = LinearLayoutManager(this)
        recycler.layoutManager = mLayoutManager
        recycler.adapter = mAdapter
            }

    private fun fetchDataFromDb() {
        val task = Runnable {
            val weatherData =
                    mDb?.DataDao()?.getAll()
            mUiHandler.post {
                if (weatherData == null || weatherData.size == 0) {
                    no_record.visibility = View.VISIBLE
                    recycler.visibility = View.GONE
                } else
                {bindDataWithUi(weatherData = weatherData.toList())
                    no_record.visibility = View.GONE
                     recycler.visibility = View.VISIBLE}
            }
        }
        mDbWorkerThread.postTask(task)
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
