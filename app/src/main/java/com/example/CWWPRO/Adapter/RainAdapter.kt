package com.example.CWWPRO.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.CWWPRO.DamViewHolder
import com.example.CWWPRO.DamViewItem
import com.example.CWWPRO.R



class RainAdapter (private val pageLists: List<RainViewItem>) : RecyclerView.Adapter<RainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rain_template, parent, false)
        return RainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pageLists.size
    }

    override fun onBindViewHolder(viewHolder: RainViewHolder, currentPage: Int) {
        val viewItem = pageLists[currentPage]
        viewHolder.bind(viewItem)
    }
}