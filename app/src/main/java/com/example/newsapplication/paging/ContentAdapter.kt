package com.example.newsapplication.paging

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.R
import com.example.newsapplication.databinding.CheckboxLayoutBinding
import com.example.newsapplication.models.Filter

class ContentAdapter(
    var notifyAdapter: () -> Unit
) : RecyclerView.Adapter<ContentAdapter.ViewHolder>() {

    var selectedContent : String? = ""

    inner class ViewHolder(val binding : CheckboxLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun setData(filter : Filter){
            binding.contentCheckboxLayout.text = filter.filter
            binding.contentCheckboxLayout.isChecked = filter.filter == selectedContent
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : CheckboxLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.checkbox_layout,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    private val diffUtilCallback = object : DiffUtil.ItemCallback<Filter>() {
        override fun areItemsTheSame(oldItem: Filter, newItem: Filter): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: Filter, newItem: Filter): Boolean {
            return false
        }
    }

    val differ = AsyncListDiffer(this, diffUtilCallback)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])

        holder.binding.contentCheckboxLayout.setOnClickListener {
            if(selectedContent != differ.currentList[position].filter){
                selectedContent = differ.currentList[position].filter
                Log.d("contentslogged","contentselected : $selectedContent")
                notifyDataSetChanged()
            }else{
                selectedContent = ""
                Log.d("contentslogged","contentselected : $selectedContent")
                notifyDataSetChanged()
            }
        }
    }
}