package com.danilovfa.cryptocurrencies.app.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.danilovfa.cryptocurrencies.R
import com.danilovfa.cryptocurrencies.app.MainActivity
import com.danilovfa.cryptocurrencies.app.viewmodel.MainViewModel
import com.danilovfa.cryptocurrencies.databinding.FragmentMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {
    private val viewModel: MainViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).title = getString(R.string.cryptocurrencies)
    }

    private fun navigateToDetailsScreen(coinId: String) {
        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(coinId)
        findNavController().navigate(action)
    }
}