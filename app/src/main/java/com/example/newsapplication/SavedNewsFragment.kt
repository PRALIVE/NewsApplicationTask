package com.example.newsapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.models.Article
import com.example.newsapplication.saveddatarecycler.DataAdapter
import com.example.newsapplication.savedroomdatabase.SavedDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavedNewsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_saved_news, container, false)
        val recyclerview = view.findViewById<RecyclerView>(R.id.recyclerviewf2)
        val database: SavedDao? = MainActivity.roomDao
        var list: List<Article>?
        CoroutineScope(Dispatchers.IO).launch {
            list = database?.getArticles()
            val list2 = ArrayList(list)

            withContext(Dispatchers.Main)  { recyclerview.adapter =
                database?.let { DataAdapter(list2, it) }
            }
        }

        return view
    }
}