package com.danilovfa.cryptocurrencies.app.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.danilovfa.cryptocurrencies.R
import com.danilovfa.cryptocurrencies.databinding.DialogNoConnectionBinding
import com.danilovfa.cryptocurrencies.utils.NetworkStatus
import com.danilovfa.cryptocurrencies.utils.extension.TAG
import com.danilovfa.cryptocurrencies.utils.extension.snack

class NoInternetDialogFragment(context: Context) : DialogFragment() {
    private var _binding: DialogNoConnectionBinding? = null
    private val binding get() = _binding!!

    private val networkStatusUtil = NetworkStatus(context)

    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog!!.window?.setLayout(width, height)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyFullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogNoConnectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tryAgainButton.setOnClickListener {
            checkInternetConnection()
        }
    }

    private fun checkInternetConnection() {
        val isConnected = networkStatusUtil.checkInternetConnection()
        if (!isConnected) {
            binding.root.snack(R.string.no_internet_connection)
        } else {
            dismiss()
        }
    }

    companion object {
        fun display(fragmentManager: FragmentManager, context: Context): NoInternetDialogFragment {
            val dialog = NoInternetDialogFragment(context)
            dialog.show(fragmentManager, TAG)
            return dialog
        }
    }
}