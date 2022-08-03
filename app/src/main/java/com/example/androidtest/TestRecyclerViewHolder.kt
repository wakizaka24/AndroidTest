package com.example.androidtest

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TestRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    val itemView1: View = view.findViewById(R.id.itemView1)
    val itemTextView1: TextView = view.findViewById(R.id.itemTextView1)

    init {
    }
}