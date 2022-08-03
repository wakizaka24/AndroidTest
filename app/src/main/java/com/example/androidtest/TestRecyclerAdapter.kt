package com.example.androidtest

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.allViews
import androidx.recyclerview.widget.RecyclerView

class TestRecyclerAdapter(private val context: Context,
                          private val itemClickListener: TestRecyclerViewHolder.ItemClickListener,
                          private val itemList:List<String>) : RecyclerView.Adapter<TestRecyclerViewHolder>() {

    private var recyclerView : RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }

    override fun onBindViewHolder(holder: TestRecyclerViewHolder, position: Int) {
        holder.let {
            it.itemTextView1.text = itemList.get(position)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val mView = layoutInflater.inflate(R.layout.recycler_view_item_test, parent, false)

        mView.setOnClickListener { view ->
            recyclerView?.let {
                itemClickListener.onItemClick(view, it.getChildAdapterPosition(view))
            }
        }

        return TestRecyclerViewHolder(mView)
    }
}