package com.danilovfa.cryptocurrencies.app.fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.TextView
import androidx.core.view.MenuProvider
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danilovfa.cryptocurrencies.R
import com.danilovfa.cryptocurrencies.app.adapter.CryptocurrenciesPageAdapter
import com.danilovfa.cryptocurrencies.app.viewmodel.MainViewModel
import com.danilovfa.cryptocurrencies.databinding.FragmentMainBinding
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrenciesOrder
import com.danilovfa.cryptocurrencies.domain.model.CryptocurrencyItem
import com.danilovfa.cryptocurrencies.utils.Constants.Companion.PER_PAGE_DEFAULT
import com.danilovfa.cryptocurrencies.utils.extension.showRadioListDialog
import com.danilovfa.cryptocurrencies.utils.extension.showTextDialog
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate), MenuProvider,
    CryptocurrenciesPageAdapter.OnItemClickListener {
    private val viewModel: MainViewModel by viewModel()
    private lateinit var cryptocurrenciesAdapter: CryptocurrenciesPageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarShowTitle(getString(R.string.cryptocurrencies))
        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        setupRecyclerView()
        observeResponses()
        initOnRefresh()

        toolbarHideIcon()
        toolbarHideBackButton()
        postponeEnterTransition()
        binding.pagingListView.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun observeResponses() {
        lifecycleScope.launch {
            viewModel.apply {
                launch {
                    cryptocurrencies.collectLatest { newList ->
                        hideProgressBar()
                        cryptocurrenciesAdapter.differ.submitList(newList)
                    }
                }
                launch {
                    errorMessage.collectLatest { errorMessage ->
                        hideProgressBar()
                        if (errorMessage.isNotEmpty())
                            requireContext().showTextDialog(R.string.error, errorMessage)
                    }
                }
                launch {
                    isLoading.collectLatest { isLoading ->
                        if (isLoading)
                            showProgressBar()
                    }
                }
            }
        }
    }

    private fun showProgressBar() {
        binding.apply {
            pagingProgressBar.visibility = View.VISIBLE
            onRefreshLayout.visibility = View.GONE
        }
    }

    private fun hideProgressBar() {
        binding.apply {
            pagingProgressBar.visibility = View.GONE
            onRefreshLayout.visibility = View.VISIBLE
        }
    }

    private fun initOnRefresh() {
        binding.onRefreshLayout.setOnRefreshListener {
            showProgressBar()
            viewModel.refresh()
            binding.onRefreshLayout.isRefreshing = false
        }
    }

    private var isLoading = false
    private var isScrolling = false

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= PER_PAGE_DEFAULT
            val shouldPaginate = !isLoading && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if(shouldPaginate) {
                viewModel.getNextPage()
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    private fun setupRecyclerView() {
        cryptocurrenciesAdapter = CryptocurrenciesPageAdapter(requireContext())
        cryptocurrenciesAdapter.setOnItemClickLister(this)
        binding.pagingListView.apply {
            adapter = cryptocurrenciesAdapter
            addOnScrollListener(this@MainFragment.scrollListener)
        }
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

    private fun navigateToDetailsScreen(coinId: String, priceTextView: TextView) {
        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(coinId)
        val extras = FragmentNavigatorExtras(
            priceTextView to coinId
        )
        findNavController().navigate(action, extras)
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

    override fun onItemClick(cryptocurrencyItem: CryptocurrencyItem?, priceTextView: TextView) {
        cryptocurrencyItem?.let { coin ->
            navigateToDetailsScreen(coin.id, priceTextView)
        }
    }
}