package com.example.CWWPRO.Adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.CWWPRO.R

class RainViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    private val datetoday = view.findViewById<TextView>(R.id.datetoday)
    private val province = view.findViewById<TextView>(R.id.province)
    private val raintoday = view.findViewById<TextView>(R.id.raintoday)
    private val pictoday = view.findViewById<ImageView>(R.id.pictoday)
    private val nextday1 = view.findViewById<TextView>(R.id.nextday1)
    private val picday1 = view.findViewById<ImageView>(R.id.picday1)
    private val nextday2 = view.findViewById<TextView>(R.id.nextday2)
    private val picday2 = view.findViewById<ImageView>(R.id.picday2)
    private val nextday3 = view.findViewById<TextView>(R.id.nextday3)
    private val picday3 = view.findViewById<ImageView>(R.id.picday3)
    private val nextday4 = view.findViewById<TextView>(R.id.nextday4)
    private val picday4 = view.findViewById<ImageView>(R.id.picday4)
    private val nextday5 = view.findViewById<TextView>(R.id.nextday5)
    private val picday5 = view.findViewById<ImageView>(R.id.picday5)
    private val nextday6 = view.findViewById<TextView>(R.id.nextday6)
    private val picday6 = view.findViewById<ImageView>(R.id.picday6)
    private val nextday7 = view.findViewById<TextView>(R.id.nextday7)
    private val picday7 = view.findViewById<ImageView>(R.id.picday7)
    private val dateday1 = view.findViewById<TextView>(R.id.dateday1)
    private val dateday2 = view.findViewById<TextView>(R.id.dateday2)
    private val dateday3 = view.findViewById<TextView>(R.id.dateday3)
    private val dateday4 = view.findViewById<TextView>(R.id.dateday4)
    private val dateday5 = view.findViewById<TextView>(R.id.dateday5)
    private val dateday6 = view.findViewById<TextView>(R.id.dateday6)
    private val dateday7 = view.findViewById<TextView>(R.id.dateday7)
    private val context = view.context



    fun bind(RainViewItem: RainViewItem) {
        datetoday.text = RainViewItem.datetoday
        province.text = RainViewItem.province
        raintoday.text = RainViewItem.raintoday.toString()

        nextday1.text = RainViewItem.nextday1.toString()
        nextday2.text = RainViewItem.nextday2.toString()
        nextday3.text = RainViewItem.nextday3.toString()
        nextday4.text = RainViewItem.nextday4.toString()
        nextday5.text = RainViewItem.nextday5.toString()
        nextday6.text = RainViewItem.nextday6.toString()
        nextday7.text = RainViewItem.nextday7.toString()


        dateday1.text = RainViewItem.dateday1
        dateday2.text = RainViewItem.dateday2
        dateday3.text = RainViewItem.dateday3
        dateday4.text = RainViewItem.dateday4
        dateday5.text = RainViewItem.dateday5
        dateday6.text = RainViewItem.dateday6
        dateday7.text = RainViewItem.dateday7



        Glide.with(context).load(RainViewItem.pictoday).into(pictoday)
        Glide.with(context).load(RainViewItem.picday1).into(picday1)
        Glide.with(context).load(RainViewItem.picday2).into(picday2)
        Glide.with(context).load(RainViewItem.picday3).into(picday3)
        Glide.with(context).load(RainViewItem.picday4).into(picday4)
        Glide.with(context).load(RainViewItem.picday5).into(picday5)
        Glide.with(context).load(RainViewItem.picday6).into(picday6)
        Glide.with(context).load(RainViewItem.picday7).into(picday7)
    }
}