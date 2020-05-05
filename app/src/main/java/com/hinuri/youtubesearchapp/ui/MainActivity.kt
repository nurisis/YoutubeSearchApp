package com.hinuri.youtubesearchapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.hinuri.youtubesearchapp.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private val viewModel : SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.my_nav_host_fragment)

        viewModel.toastMessage.observe(this, Observer {
            Toast.makeText(this, getString(it), Toast.LENGTH_LONG).show()
        })
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
