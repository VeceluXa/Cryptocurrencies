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
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.danilovfa.cryptocurrencies.R
import com.danilovfa.cryptocurrencies.app.MainActivity
import com.danilovfa.cryptocurrencies.app.viewmodel.UserSettingsViewModel
import com.danilovfa.cryptocurrencies.databinding.FragmentUserSettingsBinding
import com.danilovfa.cryptocurrencies.domain.model.User
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
        viewModel.getUser()
        setDatePicker()
        observeStates()
    }

    private fun observeStates() {
        lifecycleScope.launch {
            launch {
                viewModel.firstName.collect { firstName ->
                    binding.firstNameEditText.setText(firstName)
                }
            }
            launch {
                viewModel.lastName.collect { lastName ->
                    binding.lastNameEditText.setText(lastName)
                }
            }
            launch {
                viewModel.dateOfBirth.collect { dateOfBirth ->
                    binding.dateOfBirthEditText.setText(dateOfBirth)
                }
            }
        }
    }

    private fun setDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.select_your_birth_day))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setTheme(R.style.ThemeOverlay_App_MaterialCalendar)
            .build()
        binding.dateOfBirthEditText.setOnClickListener {
            if (!datePicker.isAdded) {
                datePicker.show(parentFragmentManager, "DATE_OF_BIRTH_PICKER")
                datePicker.addOnPositiveButtonClickListener { epoch ->
                    viewModel.saveDateOfBirth(epoch)
                }
            }
        }
    }

    private fun saveUser() {
        viewModel.saveFirstName(binding.firstNameEditText.text.toString())
        viewModel.saveLastName(binding.lastNameEditText.text.toString())
        val response = viewModel.saveUser()
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