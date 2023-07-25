package com.danilovfa.cryptocurrencies.app.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.danilovfa.cryptocurrencies.R
import com.danilovfa.cryptocurrencies.app.MainActivity

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

open class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {
    private var _binding: VB? = null
    val binding get() = _binding!!

    private var activity: MainActivity? = null

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
        if (requireActivity() is MainActivity) {
            activity = requireActivity() as MainActivity
        }
        setup()
    }

    open fun setup() {
        showBottomNavigation()
        showAppBar()
    }

    fun hideBottomNavigation() {
        val bottomNavigationToolbar = activity?.binding?.bottomNavigationView
        bottomNavigationToolbar?.visibility = View.GONE
    }

    fun showBottomNavigation() {
        val bottomNavigationToolbar = activity?.binding?.bottomNavigationView
        bottomNavigationToolbar?.visibility = View.VISIBLE
    }

    fun hideAppBar() {
        val appBar = activity?.binding?.appBarLayout
        appBar?.visibility = View.GONE
    }

    fun showAppBar() {
        val appBar = activity?.binding?.appBarLayout
        appBar?.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun toolbarShowIcon(drawable: Drawable) {
        activity?.supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayUseLogoEnabled(true)
            setLogo(drawable)
        }
    }

    fun toolbarHideIcon() {
        activity?.supportActionBar?.apply {
            setDisplayShowHomeEnabled(false)
            setDisplayUseLogoEnabled(false)
            setLogo(null)
        }
    }

    fun toolbarShowBackButton() {
        activity?.apply {
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.eva_arrow_ios_back_outline)
            }
            binding.mainToolBar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    fun toolbarHideBackButton() {
        activity?.apply {
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(false)
                setHomeAsUpIndicator(null)
            }
            binding.mainToolBar.setNavigationOnClickListener(null)
        }
    }

    fun toolbarShowTitle(title: String) {
        activity?.title = title
    }

    fun toolbarHideTitle() {
        activity?.title = ""
    }
}