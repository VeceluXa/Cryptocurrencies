package com.danilovfa.cryptocurrencies.app.fragment

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.danilovfa.cryptocurrencies.R
import com.danilovfa.cryptocurrencies.app.MainActivity
import com.danilovfa.cryptocurrencies.app.viewmodel.UserSettingsViewModel
import com.danilovfa.cryptocurrencies.databinding.FragmentUserSettingsBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class UserSettingsFragment : Fragment(), MenuProvider {
    private var _binding: FragmentUserSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserSettingsViewModel by viewModel()

    private val pickFromGalleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null)
                viewModel.saveAvatar(uri)
        }

    private val takePictureResultLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isCompleted ->
            if (isCompleted)
                viewModel.saveAvatar(getInternalAvatarUri())
        }

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
            launch {
                viewModel.avatarUri.collect { avatarUri ->
                    setAvatar(avatarUri)
                }
            }
        }
    }

    private fun setAvatarDialog() {
        binding.avatarSelectionButton.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle(R.string.download_photo)
                .setItems(R.array.avatar_dialog_array) { _, which ->
                    when (which) {
                        0 -> takePictureResultLauncher.launch(getInternalAvatarUri())
                        1 -> pickFromGalleryResultLauncher.launch("image/*")
                    }
                }
                .create()
            dialog.show()
        }
    }

    private fun setAvatar(uri: Uri) {
        Glide
            .with(this)
            .load(uri)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .centerCrop()
            .placeholder(R.drawable.baseline_person_24)
            .into(binding.avatarImageView)
    }

    private fun getInternalAvatarUri(): Uri {
        val tempImage = File(
            requireActivity().applicationContext.filesDir,
            getString(R.string.avatar_image_path)
        )
        return FileProvider.getUriForFile(
            requireActivity().applicationContext,
            getString(R.string.authorities),
            tempImage
        )
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