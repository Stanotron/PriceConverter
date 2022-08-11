package com.example.priceconverter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.convert.view.*
import java.util.*

class pcAdapter(val context : Context, var Curr : currency = currency.Usd): RecyclerView.Adapter<pcAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType : Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.convert,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return 1;
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind (position: Int){

        }
    }

}