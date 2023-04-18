package com.example.newsapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newsapplication.savedroomdatabase.SavedDao
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView
    private val fragmentManager = supportFragmentManager

    companion object {
        var roomDao: SavedDao? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottomNavigation)
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.framelayout, PagingFragment())
        fragmentTransaction.commit()
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.framelayout, PagingFragment())
                    fragmentTransaction.commit()
                    true
                }

                R.id.saved -> {
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.framelayout, SavedNewsFragment())
                    fragmentTransaction.commit()
                    true
                }
                else -> false
            }
        }
    }
}