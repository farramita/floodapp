package com.example.CWWPRO

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.CWWPRO.R



class DamAdapter(private val pageLists: List<DamViewItem>) : RecyclerView.Adapter<DamViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DamViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dam_template, parent, false)
        return DamViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pageLists.size
    }

    override fun onBindViewHolder(viewHolder: DamViewHolder, currentPage: Int) {
        val viewItem = pageLists[currentPage]
        viewHolder.bind(viewItem)
    }
}