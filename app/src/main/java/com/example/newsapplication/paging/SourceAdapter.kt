package com.example.newsapplication.paging

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.R
import com.example.newsapplication.databinding.CheckboxLayoutBinding
import com.example.newsapplication.models.Filter

class SourceAdapter(
    var notifyAdapter: () -> Unit
) : RecyclerView.Adapter<SourceAdapter.ViewHolder>() {

    var selectedSource : String? = ""

    inner class ViewHolder(val binding : CheckboxLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun setData(filter : Filter){
            binding.contentCheckboxLayout.text = filter.filter
            binding.contentCheckboxLayout.isChecked = filter.filter == selectedSource
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])

        holder.binding.contentCheckboxLayout.setOnClickListener {
            if(selectedSource != differ.currentList[position].filter){
                selectedSource = differ.currentList[position].filter
                Log.d("contentslogged","sourceselected : $selectedSource")
                notifyDataSetChanged()
            }else{
                selectedSource = ""
                Log.d("contentslogged","sourceselected : $selectedSource")
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}