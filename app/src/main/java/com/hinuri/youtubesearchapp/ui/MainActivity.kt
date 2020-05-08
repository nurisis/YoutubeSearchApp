package com.hinuri.youtubesearchapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
}
