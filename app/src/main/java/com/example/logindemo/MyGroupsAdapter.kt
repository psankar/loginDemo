package com.example.logindemo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_my_groups_row.view.*

/**
 * Created by secret on 07/03/18.
 */

class MyGroupsAdapter(private val groups: Array<String>) : RecyclerView.Adapter<MyGroupsViewHolder>() {

    override fun getItemCount(): Int {
        return groups.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyGroupsViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.layout_my_groups_row, parent, false)
        return MyGroupsViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: MyGroupsViewHolder?, position: Int) {
        holder?.view?.textView_group_name?.text = groups.get(index = position)
    }
}

class MyGroupsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}