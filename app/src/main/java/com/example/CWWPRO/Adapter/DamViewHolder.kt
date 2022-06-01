package com.example.CWWPRO

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.CWWPRO.R



class DamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name = view.findViewById<TextView>(R.id.damName)
    private val damValue1 = view.findViewById<TextView>(R.id.damValue1)
    private val damValue2 = view.findViewById<TextView>(R.id.damValue2)
    private val damValue3 = view.findViewById<TextView>(R.id.damValue3)
    private val damValue4 = view.findViewById<TextView>(R.id.damValue4)
    private val damValue5 = view.findViewById<TextView>(R.id.damValue5)
    private val damValue6 = view.findViewById<TextView>(R.id.damValue6)


    fun bind(DamViewItem: DamViewItem) {
        name.text = DamViewItem.name
        damValue1.text = DamViewItem.damValue1
        damValue2.text = DamViewItem.damValue2
        damValue3.text = DamViewItem.damValue3
        damValue4.text = DamViewItem.damValue4
        damValue5.text = DamViewItem.damValue5
        damValue6.text = DamViewItem.damValue6
    }
}

