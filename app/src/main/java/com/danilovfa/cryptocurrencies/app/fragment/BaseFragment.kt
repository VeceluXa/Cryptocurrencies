package com.danilovfa.cryptocurrencies.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.danilovfa.cryptocurrencies.app.MainActivity

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

open class BaseFragment<VB: ViewBinding>(
    private val inflate: Inflate<VB>
): Fragment() {
    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    open fun setup() {
        showBottomNavigation()
        showAppBar()
    }

    fun hideBottomNavigation() {
        val activity = requireActivity() as MainActivity
        val bottomNavigationToolbar = activity.binding.bottomNavigationView
        bottomNavigationToolbar.visibility = View.GONE
    }

    fun showBottomNavigation() {
        val activity = requireActivity() as MainActivity
        val bottomNavigationToolbar = activity.binding.bottomNavigationView
        bottomNavigationToolbar.visibility = View.VISIBLE
    }

    fun hideAppBar() {
        val activity = requireActivity() as MainActivity
        val appBar = activity.binding.appBarLayout
        appBar.visibility = View.GONE
    }

    fun showAppBar() {
        val activity = requireActivity() as MainActivity
        val appBar = activity.binding.appBarLayout
        appBar.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}