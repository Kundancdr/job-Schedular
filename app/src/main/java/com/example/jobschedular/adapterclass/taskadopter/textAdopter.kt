package com.example.jobschedular.adapterclass.taskadopter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobschedular.Activity.taskupdate
import com.example.jobschedular.R

class textAdopter(val Data:ArrayList<Pair<String,String>>, val inf: taskupdate):RecyclerView.Adapter<textAdopter.V>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): textAdopter.V {
        val lay=LayoutInflater.from(parent.context).inflate(R.layout.textlay,parent,false)
        return V(lay)
    }

    override fun onBindViewHolder(holder: textAdopter.V, position: Int) {
val current=Data[position]
        holder.teskshow.text=current.first
        holder.dateshow.text=current.second
    }

    override fun getItemCount(): Int {
        return Data.size
    }
    inner class V(v:View):RecyclerView.ViewHolder(v){
val teskshow:TextView=v.findViewById(R.id.textshow)
        val dateshow:TextView=v.findViewById(R.id.dateshow)
        val taskdatecontainorcard:CardView=v.findViewById(R.id.taskdatecontainorcard)
        init {
            taskdatecontainorcard.setOnClickListener {
                inf.ontaskclick(layoutPosition)
            }
        }
    }
    interface taskclick{
        fun ontaskclick(position: Int)
    }
}