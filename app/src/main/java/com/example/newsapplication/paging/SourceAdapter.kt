package com.example.newsapplication.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.R

class SourceAdapter(val contentFilter : ArrayList<String>) : RecyclerView.Adapter<SourceAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.checkbox_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current : String = contentFilter[position]

    }

    override fun getItemCount(): Int {
        return contentFilter.size
    }
}