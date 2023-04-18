package com.example.newsapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.newsapplication.databinding.FragmentPagingBinding
import com.example.newsapplication.models.Article
import com.example.newsapplication.paging.NewsPagingAdapter
import com.example.newsapplication.savedroomdatabase.SavedDao
import com.example.newsapplication.savedroomdatabase.SavedDatabase
import com.example.newsapplication.viemodel.NewsViewModel

class PagingFragment : Fragment() {

    private lateinit var recyclerview: RecyclerView
    private lateinit var adapter: NewsPagingAdapter
    private lateinit var newsviewmodel: NewsViewModel
    private var contentFilter = ""
    private var sourceFilter = ""
    lateinit var database : SavedDao
    private var currentData: PagingData<Article>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsviewmodel = ViewModelProvider(requireActivity())[NewsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentPagingBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_paging, container, false);
        val room = Room.databaseBuilder(
            requireContext(),
            SavedDatabase::class.java,"SavedArticles"
        ).fallbackToDestructiveMigration().build()
        val roomdao = room.savedDao()
        MainActivity.roomDao=roomdao
        database = roomdao

        recyclerview = binding.newsRecyclerView
        adapter = NewsPagingAdapter(roomdao)


        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.adapter = adapter

        observerData()

        binding.filterImage.setOnClickListener {
            if(binding.subFilter.isVisible){
                binding.subFilter.visibility = View.GONE
            }else{
                binding.subFilter.visibility = View.VISIBLE
            }
        }

        binding.contentCheckbox1.setOnClickListener {
            contentFilter = binding.contentCheckbox1.text.toString()
            binding.contentCheckbox2.isChecked = false
            binding.contentCheckbox3.isChecked = false
            binding.contentCheckbox4.isChecked = false
        }

        binding.contentCheckbox2.setOnClickListener {
            contentFilter = binding.contentCheckbox2.text.toString()
            binding.contentCheckbox1.isChecked = false
            binding.contentCheckbox3.isChecked = false
            binding.contentCheckbox4.isChecked = false
        }
        binding.contentCheckbox3.setOnClickListener {
            contentFilter = binding.contentCheckbox3.text.toString()
            binding.contentCheckbox1.isChecked = false
            binding.contentCheckbox2.isChecked = false
            binding.contentCheckbox4.isChecked = false
        }
        binding.contentCheckbox4.setOnClickListener {
            contentFilter = binding.contentCheckbox4.text.toString()
            binding.contentCheckbox1.isChecked = false
            binding.contentCheckbox2.isChecked = false
            binding.contentCheckbox3.isChecked = false
        }

        binding.sourceCheckbox1.setOnClickListener {
            sourceFilter = binding.sourceCheckbox1.text.toString()
            binding.sourceCheckbox2.isChecked = false
        }

        binding.sourceCheckbox2.setOnClickListener {
            sourceFilter = binding.sourceCheckbox2.text.toString()
            binding.sourceCheckbox1.isChecked = false

        }

        binding.apply.setOnClickListener {
            handleCheckBox(binding)
            val filteredData = currentData?.let { it1 -> filterData(it1) }
            if (filteredData != null) {
                adapter.submitData(lifecycle, filteredData)
            }
            binding.subFilter.visibility = View.GONE
        }
        return binding.root
    }

    private fun observerData(){
        newsviewmodel.list.observe(viewLifecycleOwner) {
            currentData = it
            val filteredData = filterData(it)
            adapter.submitData(lifecycle, filteredData)
        }
    }

    private fun filterData(data: PagingData<Article>) : PagingData<Article>{
        var filteredData = data
        if(contentFilter != "") {
            filteredData = data.filter { it.description?.contains(contentFilter, ignoreCase = true) ?: false }
        }
        if(sourceFilter!=""){
            filteredData = filteredData.filter { it.source?.name?.contains(sourceFilter,ignoreCase= true) ?: false }
        }
        return filteredData
    }

    private fun handleCheckBox(binding: FragmentPagingBinding){
        if(!binding.contentCheckbox1.isChecked && !binding.contentCheckbox2.isChecked && !binding.contentCheckbox3.isChecked && !binding.contentCheckbox4.isChecked){
            contentFilter=""
        }

        if(!binding.sourceCheckbox1.isChecked && !binding.sourceCheckbox2.isChecked){
            sourceFilter=""
        }
    }
}