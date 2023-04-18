package com.example.newsapplication

import android.os.Bundle
import android.util.Log
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
import com.example.newsapplication.models.Filter
import com.example.newsapplication.paging.ContentAdapter
import com.example.newsapplication.paging.NewsPagingAdapter
import com.example.newsapplication.paging.SourceAdapter
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
    var contentAdapter : ContentAdapter? = null
    var sourceAdapter  : SourceAdapter? = null
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

        setAdapter(binding,roomdao)
        setContentAdapter(binding)
        setSourceAdapter(binding)
        observerData()
        filterImageListener(binding)
        applyButtonListener(binding)
        contentFilterListener(binding)
        sourceFilterListener(binding)
        return binding.root
    }

    private fun setSourceAdapter(binding: FragmentPagingBinding) {
        val sourceList = getSourceList()
        sourceAdapter = SourceAdapter{sourceAdapter?.differ?.submitList(sourceList)}
        binding.sourceFilterRecyclerView.adapter = sourceAdapter
        sourceAdapter?.differ?.submitList(sourceList)
    }

    private fun getSourceList() : List<Filter>{
        val content1 = Filter(1,"Marketscreener.com")
        val content2 = Filter(2,"Investing.com")

        val source = listOf<Filter>(
            content1,
            content2,
        )
        return source
    }

    private fun setContentAdapter(binding: FragmentPagingBinding) {
        val contentList = getContentList()
        contentAdapter = ContentAdapter { contentAdapter?.differ?.submitList(contentList) }
        binding.contentFilterRecyclerView.adapter = contentAdapter
        contentAdapter?.differ?.submitList(contentList)
    }

    private fun getContentList() : List<Filter>{
        val content1 = Filter(1,"Elon Musk")
        val content2 = Filter(2,"Tesla")
        val content3 = Filter(3,"Vehicle")
        val content4 = Filter(4,"Austin")

        val content = listOf<Filter>(
            content1,
            content2,
            content3,
            content4
        )
        return content
    }

    private fun sourceFilterListener(binding: FragmentPagingBinding) {
        binding.sourceFilter.setOnClickListener {
            if(binding.sourceFilterRecyclerView.isVisible){
                binding.sourceFilterRecyclerView.visibility = View.GONE
            }else{
                binding.sourceFilterRecyclerView.visibility = View.VISIBLE
            }
        }
    }

    private fun contentFilterListener(binding: FragmentPagingBinding) {
          binding.contentFilter.setOnClickListener {
              if(binding.contentFilterRecyclerView.isVisible){
                  binding.contentFilterRecyclerView.visibility = View.GONE
              }else{
                  binding.contentFilterRecyclerView.visibility = View.VISIBLE
              }
          }
    }

    private fun applyButtonListener(binding: FragmentPagingBinding) {
        binding.apply.setOnClickListener {
            contentFilter = contentAdapter?.selectedContent.toString()
            sourceFilter = sourceAdapter?.selectedSource.toString()
            Log.d("contentslogged","$contentFilter")
            Log.d("contentslogged","$sourceFilter")
            val filteredData = currentData?.let { it1 -> filterData(it1) }
            if (filteredData != null) {
                adapter.submitData(lifecycle, filteredData)
            }
            binding.subFilter.visibility = View.GONE
        }
    }

    private fun filterImageListener(binding: FragmentPagingBinding) {
        binding.filterImage.setOnClickListener {
            if(binding.subFilter.isVisible){
                binding.subFilter.visibility = View.GONE
            }else{
                binding.subFilter.visibility = View.VISIBLE
            }
        }
    }

    private fun setAdapter(binding : FragmentPagingBinding,roomdao : SavedDao ){
        recyclerview = binding.newsRecyclerView
        adapter = NewsPagingAdapter(roomdao)
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.adapter = adapter
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
}