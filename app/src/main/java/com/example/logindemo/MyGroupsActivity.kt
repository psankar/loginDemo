package com.example.logindemo

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_my_groups.*

class MyGroupsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_groups)

        recyclerView_mygroups.layoutManager = LinearLayoutManager(this)
        recyclerView_mygroups.adapter = MyGroupsAdapter(arrayOf())

        val fetchTask = MyGroupsFetchTask()
        fetchTask.execute(null as Void?)

    }

    inner class MyGroupsFetchTask internal constructor() : AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            Log.i("MyGroupsFetchTask", intent.getStringExtra("JWT"))
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            recyclerView_mygroups.adapter = MyGroupsAdapter(arrayOf("Hello", "Kotlin"))
        }

    }
}
