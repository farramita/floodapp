package com.example.CWWPRO.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.CWWPRO.R

class ModelAdapter(private val pageLists: List<ModelViewItem>) : RecyclerView.Adapter<ModelViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.model_template, parent, false)
        return ModelViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pageLists.size
    }

    override fun onBindViewHolder(viewHolder: ModelViewHolder, currentPage: Int) {
        val viewItem = pageLists[currentPage]
        viewHolder.bind(viewItem)
    }
}