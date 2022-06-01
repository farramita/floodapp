package com.example.CWWPRO.Adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.CWWPRO.R

class DetailMapPointViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val deM_date = view.findViewById<TextView>(R.id.deM_date)
    private val deM_dis = view.findViewById<TextView>(R.id.deM_dis)
    private val deM_province = view.findViewById<TextView>(R.id.deM_province)
    private val deM_predict = view.findViewById<TextView>(R.id.deM_predict)
    private val deM_rain = view.findViewById<TextView>(R.id.deM_rain)

    fun bind(DetailMapPointViewItem: DetailMapPointViewItem) {
        deM_date.text = DetailMapPointViewItem.deM_date
        deM_dis.text = DetailMapPointViewItem.deM_dis
        deM_province.text = DetailMapPointViewItem.deM_province
        deM_predict.text = DetailMapPointViewItem.deM_predict
        deM_rain.text = DetailMapPointViewItem.deM_rain
    }
}