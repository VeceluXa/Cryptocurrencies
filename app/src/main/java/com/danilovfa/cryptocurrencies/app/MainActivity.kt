package com.danilovfa.cryptocurrencies.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.danilovfa.cryptocurrencies.R
import com.danilovfa.cryptocurrencies.app.viewmodel.SplashViewModel
import com.danilovfa.cryptocurrencies.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bottom navigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        val navView: BottomNavigationView = binding.bottomNavigationView
        navView.setupWithNavController(navController)

        // Toolbar
        val toolbar = binding.mainToolBar
        setSupportActionBar(toolbar)
    }
}