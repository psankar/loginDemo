package com.example.logindemo

import UnsafeOkHttpClient
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_my_groups.*
import okhttp3.Request

class MyGroupsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_groups)

        recyclerView_mygroups.layoutManager = LinearLayoutManager(this)
        //recyclerView_mygroups.adapter = MyGroupsAdapter(arrayOf())

        val fetchTask = MyGroupsFetchTask()
        fetchTask.execute(null as Void?)
    }

    inner class MyGroupsFetchTask internal constructor() : AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {

            val jwt = intent.getStringExtra("JWT")
            Log.i("MyGroupsFetchTask", jwt)


            val urlPath = "https://10.0.2.2:8000/my-groups"

            val request = Request.Builder().url(urlPath).get()
                    .addHeader("Authorization", "Bearer $jwt").build()

            val client = UnsafeOkHttpClient.getUnsafeOkHttpClient().build()

            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val gson = GsonBuilder().create()
                val groups = gson.fromJson(response.body()?.string(), MyGroupsRes::class.java).Groups

                runOnUiThread {
                    recyclerView_mygroups.adapter = MyGroupsAdapter(groups)
                }

                return true
            } else {
                Log.e("UserLoginTask", "Failure response from the server: " + response?.body()?.string())
                return false
            }

        }

        override fun onPostExecute(result: Boolean?) {
        }

    }
}

class MyGroupsRes(val Groups: Array<String>)
