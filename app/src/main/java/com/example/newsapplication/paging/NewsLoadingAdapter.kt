package com.example.newsapplication.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.R

class NewsLoadingAdapter : LoadStateAdapter<NewsLoadingAdapter.Loaderviewholder>() {

    class Loaderviewholder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val progressBar = itemView.findViewById<ProgressBar>(R.id.progressBar)
        val error = itemView.findViewById<TextView>(R.id.errormessage)
        fun bind(loadstate : LoadState){
            progressBar.isVisible = loadstate is LoadState.Loading
            if(loadstate is LoadState.Error){
                error.setVisibility(View.VISIBLE)
            }else{
                error.setVisibility(View.INVISIBLE)
            }
        }
    }

    override fun onBindViewHolder(holder: Loaderviewholder, loadState: LoadState) {
       holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): Loaderviewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.loader_item,parent,false)
        return Loaderviewholder(view)
    }
}
