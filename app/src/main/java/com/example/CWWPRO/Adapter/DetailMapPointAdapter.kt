package com.example.foodie.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.CWWPRO.Adapter.DetailMapPointViewHolder
import com.example.CWWPRO.Adapter.DetailMapPointViewItem
import com.example.CWWPRO.R

class DetailMapPointAdapter(private val pageLists: List<DetailMapPointViewItem>) : RecyclerView.Adapter<DetailMapPointViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailMapPointViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.detailmap_template, parent, false)
        return DetailMapPointViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pageLists.size
    }

    override fun onBindViewHolder(viewHolder: DetailMapPointViewHolder, currentPage: Int) {
        val viewItem = pageLists[currentPage]
        viewHolder.bind(viewItem)
    }
}