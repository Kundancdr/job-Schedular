package com.example.jobschedular.adapterclass.Anaadopter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.animation.LIB.AnimationKC
import com.example.jobschedular.R
import com.example.jobschedular.adapterclass.todays.tData


class AnaAdopter(val data: ArrayList<tData>) : RecyclerView.Adapter<AnaAdopter.viewholder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.anarecycler, parent, false)
        return viewholder(layout)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val current = data[position]
        val colorResId = if (position % 2 == 0) R.color.card2 else R.color.card1
        val color = ContextCompat.getColor(holder.itemView.context, colorResId)
        holder.Card.setCardBackgroundColor(color)
        holder.prograssbar.progress=current.prograss
        holder.percentage.text=current.prograss.toString()
        holder.title.text=current.title



    }

    class viewholder(v: View) : RecyclerView.ViewHolder(v) {
        val Card: CardView = v.findViewById(R.id.tlay)
        val backimage: ImageView = v.findViewById(R.id.tlayimage)
        val icon: ImageView = v.findViewById(R.id.tlayimageicon)
        val prograssbar: ProgressBar = v.findViewById(R.id.talyprogressBar2)
        val percentage: TextView =v.findViewById(R.id.tlaypercentage)
        val title: TextView =v.findViewById(R.id.tlaytitle)

    }
}