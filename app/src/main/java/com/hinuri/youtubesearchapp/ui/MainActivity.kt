package com.hinuri.youtubesearchapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.hinuri.youtubesearchapp.R

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.my_nav_host_fragment)
    }

    // ==========================
    // Set custom toolbar
    // ==========================
    fun setToolBar(title: String, isHomeButton: Boolean, isCloseButton:Boolean=true) {
        // set Action bar
        setSupportActionBar(findViewById(R.id.toolbar))

        // 타이틀
        val textView = findViewById<TextView>(R.id.toolbar_title)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        textView.text = title

        // 백버튼 설정
        supportActionBar?.setDisplayHomeAsUpEnabled(isHomeButton)
        supportActionBar?.setHomeAsUpIndicator(getDrawable(R.drawable.ic_arrow_back_black))

        // 닫기 버튼 설정
        findViewById<ImageView>(R.id.toolbar_close).visibility = if(isCloseButton) View.VISIBLE else View.GONE
    }
}
