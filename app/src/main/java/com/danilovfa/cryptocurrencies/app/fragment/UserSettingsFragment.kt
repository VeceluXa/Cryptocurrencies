package com.danilovfa.cryptocurrencies.app.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.danilovfa.cryptocurrencies.R
import com.danilovfa.cryptocurrencies.app.viewmodel.UserSettingsViewModel
import com.danilovfa.cryptocurrencies.databinding.FragmentUserSettingsBinding
import com.danilovfa.cryptocurrencies.utils.extension.loadImageByUriNonCached
import com.danilovfa.cryptocurrencies.utils.extension.showListDialog
import com.danilovfa.cryptocurrencies.utils.extension.snack
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserSettingsFragment :
    BaseFragment<FragmentUserSettingsBinding>(FragmentUserSettingsBinding::inflate), MenuProvider {
    private val viewModel: UserSettingsViewModel by viewModel()

    private val pickFromGalleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                viewModel.saveGalleryAvatar(requireContext(), uri)
                updateAvatar()
            }
        }

    private val takePictureResultLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isCompleted ->
            if (isCompleted) updateAvatar()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarShowTitle(getString(R.string.settings))
        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        viewModel.getUser()
        binding.root.loadImageByUriNonCached(
            image = viewModel.getInternalAvatarUri(requireContext()),
            container = binding.avatarImageView,
            placeholder = R.drawable.baseline_person_24
        )
        setDatePicker()
        setAvatarDialog()
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

    private fun setAvatarDialog() {
        binding.avatarSelectionButton.setOnClickListener {
            requireContext().showListDialog(
                title = R.string.download_photo,
                items = R.array.avatar_dialog_array,
            ) { id ->
                when (id) {
                    0 -> takePictureResultLauncher.launch(
                        viewModel.getInternalAvatarUri(
                            requireContext()
                        )
                    )

                    1 -> pickFromGalleryResultLauncher.launch("image/*")
                }
            }
        }
    }

    private fun updateAvatar() {
        binding.root.loadImageByUriNonCached(
            image = viewModel.getInternalAvatarUri(requireContext()),
            container = binding.avatarImageView,
            placeholder = R.drawable.baseline_person_24
        )
        binding.settingsCoordinatorLayout.snack(R.string.update_avatar)
    }

    private fun setDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.select_your_birth_day))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setTheme(R.style.ThemeOverlay_App_MaterialCalendar).build()
        binding.dateOfBirthEditText.setOnClickListener {
            if (!datePicker.isAdded) {
                datePicker.show(parentFragmentManager, "DATE_OF_BIRTH_PICKER")
                datePicker.addOnPositiveButtonClickListener { epoch ->
                    viewModel.saveDateOfBirth(epoch)
                }
            }
        }
    }

    fun saveUser() {
        viewModel.saveFirstName(binding.firstNameEditText.text.toString())
        viewModel.saveLastName(binding.lastNameEditText.text.toString())
        val response = viewModel.saveUser()
        binding.settingsCoordinatorLayout.snack(response)
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