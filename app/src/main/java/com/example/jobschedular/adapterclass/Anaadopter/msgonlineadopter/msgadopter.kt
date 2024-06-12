package com.example.jobschedular.adapterclass.Anaadopter.msgonlineadopter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jobschedular.R


class msgadopter : RecyclerView.Adapter<msgadopter.views>() {
    class views(v: View): RecyclerView.ViewHolder(v) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): views {
        val lay= LayoutInflater.from(parent.context).inflate(R.layout.members,parent,false)
        return views(lay)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: views, position: Int) {

    }
}