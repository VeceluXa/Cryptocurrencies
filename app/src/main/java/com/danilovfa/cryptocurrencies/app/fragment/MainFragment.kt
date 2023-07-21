package com.danilovfa.cryptocurrencies.app.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.danilovfa.cryptocurrencies.R
import com.danilovfa.cryptocurrencies.app.MainActivity
import com.danilovfa.cryptocurrencies.app.viewmodel.MainViewModel
import com.danilovfa.cryptocurrencies.databinding.FragmentMainBinding
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrenciesOrder
import com.danilovfa.cryptocurrencies.utils.extension.showRadioListDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate), MenuProvider {
    private val viewModel: MainViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).title = getString(R.string.cryptocurrencies)
        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun showSortDialog() {
        val orders = CryptocurrenciesOrder.values()
        val ordersStrings = orders.map { order ->
            getString(order.resourceId)
        }.toTypedArray()

        requireContext().showRadioListDialog(
            title = R.string.sort,
            items = ordersStrings,
            initialSelectedItemId = viewModel.sortOrder.value.ordinal
        ) { selectedItem ->
            viewModel.changeSortOrder(orders[selectedItem])
        }
    }

    private fun navigateToDetailsScreen(coinId: String) {
        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(coinId)
        findNavController().navigate(action)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_toolbar_main, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_sort -> {
                showSortDialog()
                true
            }

            else -> false
        }
    }
}