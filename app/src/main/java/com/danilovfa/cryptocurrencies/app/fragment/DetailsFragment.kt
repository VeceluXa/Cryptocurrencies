package com.danilovfa.cryptocurrencies.app.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.danilovfa.cryptocurrencies.app.MainActivity
import com.danilovfa.cryptocurrencies.app.viewmodel.DetailsViewModel
import com.danilovfa.cryptocurrencies.databinding.FragmentDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {
    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: DetailsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).title = args.coinId
    }
}