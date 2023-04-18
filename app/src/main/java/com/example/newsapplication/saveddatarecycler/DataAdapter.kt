package com.example.newsapplication.saveddatarecycler

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.R
import com.example.newsapplication.models.Article
import com.example.newsapplication.savedroomdatabase.SavedDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataAdapter(private var itemsList: ArrayList<Article>, val saveddao: SavedDao) :
    RecyclerView.Adapter<DataAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.title)
        val author = itemView.findViewById<TextView>(R.id.author)
        val date = itemView.findViewById<TextView>(R.id.date)
        val btn = itemView.findViewById<Button>(R.id.button)
        val delbtn = itemView.findViewById<Button>(R.id.delete)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val curr = itemsList[position]
        Log.d("primarykeys",curr.primaryKey.toString())
        if (curr != null) {
            holder.author.text = curr.source?.name
            holder.title.text = curr.title
            holder.date.text = curr.author
            holder.btn.setVisibility(View.GONE)
            holder.delbtn.setVisibility(View.VISIBLE)
            holder.delbtn.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch { saveddao.delete(curr)
                    withContext(Dispatchers.Main) { Toast.makeText(holder.author.context, "Data Removed "+curr.primaryKey, Toast.LENGTH_SHORT).show() }
                  }
                itemsList.removeAt(position)
                notifyDataSetChanged()
                }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}