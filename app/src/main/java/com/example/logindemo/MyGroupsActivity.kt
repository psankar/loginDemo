package com.example.logindemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_my_groups.*

class MyGroupsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_groups)

        recyclerView_mygroups.layoutManager = LinearLayoutManager(this)
        recyclerView_mygroups.adapter = MyGroupsAdapter(arrayOf("Hello", "ClientApp"))
    }
}
