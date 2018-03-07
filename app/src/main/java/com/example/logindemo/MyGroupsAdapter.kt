package com.example.logindemo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by secret on 07/03/18.
 */

class MyGroupsAdapter : RecyclerView.Adapter<MyGroupsViewHolder>() {

    override fun getItemCount(): Int {
        return 2;
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyGroupsViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.layout_my_groups_row, parent, false)
        return MyGroupsViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: MyGroupsViewHolder?, position: Int) {

    }
}

class MyGroupsViewHolder(v: View) : RecyclerView.ViewHolder(v) {

}