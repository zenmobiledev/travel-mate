package com.example.travelmate.presentation.feature.profile

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil3.load
import coil3.request.error
import com.example.travelmate.R
import com.example.travelmate.databinding.CustomDialogCategoryBinding
import com.example.travelmate.databinding.FragmentProfileBinding
import com.example.travelmate.presentation.feature.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            profileViewModel.profileUser()
            profileViewModel.user.collect {
                it?.let {
                    binding.tvName.text = it.email
                    binding.tvEmail.text = it.name
                    binding.imageViewPhotoUrl.load(it.photoUrl) {
                        error(R.drawable.outline_broken_image_24)
                    }
                }
            }
        }

        binding.btnChangeDestination.setOnClickListener {
            val dialogBinding = CustomDialogCategoryBinding.inflate(layoutInflater)
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setView(dialogBinding.root)

            val dialog = alertDialogBuilder.create()
            dialog.setCancelable(false)
            lifecycleScope.launch {
                val category = profileViewModel.getCategory()

                dialogBinding.checkBoxBeach.isChecked = category["Beach"] ?: false
                dialogBinding.checkBoxMountain.isChecked = category["Mountain"] ?: false
                dialogBinding.checkBoxCultural.isChecked = category["Cultural"] ?: false
                dialogBinding.checkBoxCulinary.isChecked = category["Culinary"] ?: false
            }

            with(dialogBinding) {
                checkBoxBeach.setOnCheckedChangeListener { _, _ ->
                    updateStartJourneyButtonState(
                        this
                    )
                }
                checkBoxMountain.setOnCheckedChangeListener { _, _ ->
                    updateStartJourneyButtonState(
                        this
                    )
                }
                checkBoxCultural.setOnCheckedChangeListener { _, _ ->
                    updateStartJourneyButtonState(
                        this
                    )
                }
                checkBoxCulinary.setOnCheckedChangeListener { _, _ ->
                    updateStartJourneyButtonState(
                        this
                    )
                }
            }

            dialogBinding.btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            dialogBinding.btnSave.setOnClickListener {
                lifecycleScope.launch {
                    profileViewModel.saveCategory(
                        beach = dialogBinding.checkBoxBeach.isChecked,
                        mountain = dialogBinding.checkBoxMountain.isChecked,
                        cultural = dialogBinding.checkBoxCultural.isChecked,
                        culinary = dialogBinding.checkBoxCulinary.isChecked
                    )
                }
                dialog.dismiss()
            }

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }

        binding.btnLogout.setOnClickListener {
            lifecycleScope.launch {
                profileViewModel.logout()
                val intent = Intent(requireContext(), LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }

                startActivity(intent)
                requireActivity().finish()
            }
        }
    }

    // check if any checkbox is checked
    private fun updateStartJourneyButtonState(binding: CustomDialogCategoryBinding) {
        val isAnyChecked = binding.checkBoxBeach.isChecked ||
                binding.checkBoxMountain.isChecked ||
                binding.checkBoxCultural.isChecked ||
                binding.checkBoxCulinary.isChecked

        binding.btnSave.isEnabled = isAnyChecked
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}