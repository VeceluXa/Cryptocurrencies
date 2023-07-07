package com.danilovfa.cryptocurrencies.app.fragment

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.danilovfa.cryptocurrencies.R
import com.danilovfa.cryptocurrencies.app.MainActivity
import com.danilovfa.cryptocurrencies.app.viewmodel.UserSettingsViewModel
import com.danilovfa.cryptocurrencies.databinding.FragmentUserSettingsBinding
import com.danilovfa.cryptocurrencies.domain.model.User
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserSettingsFragment : Fragment(), MenuProvider {
    private var _binding: FragmentUserSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserSettingsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserSettingsBinding.inflate(inflater, container, false)
        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).title = getString(R.string.settings)
    }

    private fun saveUser() {
        val user = User(
            firstName = binding.firstNameEditText.text.toString(),
            lastName = binding.lastNameEditText.text.toString(),
            dateOfBirth = null,
            avatarUri = Uri.EMPTY
        )
        val response = viewModel.saveUser(user)
        Snackbar.make(binding.settingsCoordinatorLayout, getString(response), Snackbar.LENGTH_LONG)
            .show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_toolbar_settings, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_save -> {
                saveUser()
                true
            }

            else -> false
        }
    }
}