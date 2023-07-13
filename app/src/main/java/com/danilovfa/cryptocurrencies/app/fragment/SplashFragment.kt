package com.danilovfa.cryptocurrencies.app.fragment

import android.os.Bundle
import android.view.View
import com.danilovfa.cryptocurrencies.app.viewmodel.SplashViewModel
import com.danilovfa.cryptocurrencies.databinding.FragmentSplashBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    val viewModel: SplashViewModel by viewModel()

    override fun setup() {
        super.setup()
        hideAppBar()
        hideBottomNavigation()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}