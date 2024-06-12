package com.example.jobschedular.adapterclass.todays.upcoming

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.animation.LIB.AnimationKC
import com.example.jobschedular.R
import com.example.jobschedular.adapterclass.upcoming.udata


class Uadopter (val data:ArrayList<udata>,val context: Context): RecyclerView.Adapter<Uadopter.viewholder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val layout= LayoutInflater.from(parent.context).inflate(R.layout.ulay,parent,false)
        return viewholder(layout)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(h: viewholder, position: Int) {
        val ani=AnimationKC(context)
        val current=data[position]
        h.title.text=current.title
        h.subtitle.text=current.date
        ani.AnimationStater(h.cardulay,ani.short_toright)
    }
    class viewholder(v: View): RecyclerView.ViewHolder(v){
        val title:TextView=v.findViewById(R.id.ulaytitle)
        val subtitle:TextView=v.findViewById(R.id.ulaysubtitle)
        val ImageView:ImageView=v.findViewById(R.id.ulayicon)
        val ImageButton:ImageButton=v.findViewById(R.id.ulaybutton)
        val cardulay:CardView=v.findViewById(R.id.cardulay)
    }
}