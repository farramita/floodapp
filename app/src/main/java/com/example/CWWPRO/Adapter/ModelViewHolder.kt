package com.example.CWWPRO.Adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.CWWPRO.R

class ModelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val modeltoday = view.findViewById<TextView>(R.id.modeltoday)
    private val modeloutput = view.findViewById<TextView>(R.id.modeloutput)
    private val modeldis = view.findViewById<TextView>(R.id.modeldis)
    private val modelprovince = view.findViewById<TextView>(R.id.modelprovince)
    private val modelday1 = view.findViewById<TextView>(R.id.modelday1)
    private val modelout1 = view.findViewById<TextView>(R.id.modelout1)
   // private val modelpic1 = view.findViewById<TextView>(R.id.modelpic1)
    private val modelday2 = view.findViewById<TextView>(R.id.modelday2)
    private val modelout2 = view.findViewById<TextView>(R.id.modelout2)
   // private val modelpic2 = view.findViewById<TextView>(R.id.modelpic2)
    private val modelday3 = view.findViewById<TextView>(R.id.modelday3)
    private val modelout3 = view.findViewById<TextView>(R.id.modelout3)
   // private val modelpic3 = view.findViewById<TextView>(R.id.modelpic3)



    fun bind(ModelViewItem: ModelViewItem) {
        modeltoday.text = ModelViewItem.modeltoday
        modeloutput.text = ModelViewItem.modeloutput
        modeldis.text = ModelViewItem.modeldis
        modelprovince.text = ModelViewItem.modelprovince
        modelday1.text = ModelViewItem.modelday1
       modelout1.text = ModelViewItem.modelout1
       // modelpic1.text = ModelViewItem.modelpic1
        modelday2.text = ModelViewItem.modelday2
        modelout2.text = ModelViewItem.modelout2
       // modelpic2.text = ModelViewItem.modelpic2
        modelday3.text = ModelViewItem.modelday3
        modelout3.text = ModelViewItem.modelout3
       // modelpic3.text = ModelViewItem.modelpic3

    }
}