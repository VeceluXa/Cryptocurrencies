package com.danilovfa.cryptocurrencies.app

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.danilovfa.cryptocurrencies.R
import com.danilovfa.cryptocurrencies.app.fragment.NoInternetDialogFragment
import com.danilovfa.cryptocurrencies.app.service.NetworkService
import com.danilovfa.cryptocurrencies.app.viewmodel.SplashViewModel
import com.danilovfa.cryptocurrencies.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), NetworkService.InternetCheckListener {
    lateinit var binding: ActivityMainBinding
    private val viewModel: SplashViewModel by viewModel()

    private lateinit var networkService: NetworkService
    private var isServiceBound = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as NetworkService.LocalBinder
            networkService = binder.getService()
            networkService.setInternetCheckListener(this@MainActivity)
            isServiceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isServiceBound = false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startService()

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

    override fun onDestroy() {
        super.onDestroy()
        stopService()
    }

    private fun startService() {
        val intent = Intent(this, NetworkService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun stopService() {
        if (isServiceBound) {
            unbindService(serviceConnection)
            isServiceBound = false
        }
    }

    override fun onInternetStatusChanged(isConnected: Boolean) {
        if (!isConnected)
            showNoConnectionDialog()
    }

    private fun showNoConnectionDialog() {
        NoInternetDialogFragment.display(
            fragmentManager = supportFragmentManager,
            context = this
        )
    }
}