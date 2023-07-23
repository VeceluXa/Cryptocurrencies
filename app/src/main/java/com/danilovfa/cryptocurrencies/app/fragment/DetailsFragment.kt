package com.danilovfa.cryptocurrencies.app.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.danilovfa.cryptocurrencies.R
import com.danilovfa.cryptocurrencies.app.viewmodel.DetailsViewModel
import com.danilovfa.cryptocurrencies.databinding.FragmentDetailsBinding
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyDetails
import com.danilovfa.cryptocurrencies.utils.extension.showTextDialog
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {
    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: DetailsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDetails(args.coinId)
        observeResponses()
    }

    private fun observeResponses() {
        lifecycleScope.launch {
            viewModel.apply {
                launch {
                    details.collectLatest { details ->
                        hideProgressBar()
                        if (details != null)
                            setupUiData(details)
                    }
                }
                launch {
                    errorMessage.collectLatest { errorMessage ->
                        hideProgressBar()
                        if (errorMessage.isNotEmpty())
                            requireContext().showTextDialog(R.string.error, errorMessage) {
                                findNavController().navigateUp()
                            }
                    }
                }
                launch {
                    isLoading.collectLatest { isLoading ->
                        if (isLoading)
                            showProgressBar()
                        else
                            hideProgressBar()
                    }
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.apply {
            detailsLayout.visibility = View.VISIBLE
            detailsProgressBar.visibility = View.GONE
        }
    }

    private fun showProgressBar() {
        binding.apply {
            detailsLayout.visibility = View.GONE
            detailsProgressBar.visibility = View.VISIBLE
        }
    }

    private fun setupUiData(data: CryptocurrencyDetails) {
        
    }
}