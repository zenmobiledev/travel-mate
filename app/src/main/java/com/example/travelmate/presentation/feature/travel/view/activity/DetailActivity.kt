package com.example.travelmate.presentation.feature.travel.view.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil3.load
import com.example.travelmate.R
import com.example.travelmate.databinding.ActivityDetailBinding
import com.example.travelmate.databinding.CustomDialogBinding
import com.example.travelmate.domain.model.destination.DestinationUser
import com.example.travelmate.domain.model.destination.Itinerary
import com.example.travelmate.presentation.feature.travel.viewmodel.TravelViewModel
import com.example.travelmate.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var travelViewModel: TravelViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        travelViewModel = ViewModelProvider(this@DetailActivity)[TravelViewModel::class]

        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val destination = intent.getParcelableExtra<DestinationUser.Destination>(EXTRA_DESTINATION)
        val itinerary = intent.getParcelableExtra<Itinerary>(EXTRA_ITINERARY)
        when {
            destination != null -> getDetail(destination)
            itinerary != null -> getDetail(itinerary)
        }

        binding.toolbarDetail.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        lifecycleScope.launch {
            travelViewModel.getItinerary((destination?.id).toString())
                .collect { newItinerary ->
                    if (newItinerary != null) {
                        binding.fabDetail.setImageResource(R.drawable.outline_edit_24)
                        binding.fabDetail.setOnClickListener {
                            showAlertDialog(destination = destination, itinerary = newItinerary) {
                                lifecycleScope.launch {
                                    withContext(Dispatchers.IO) {
                                        travelViewModel.updateItinerary(it)
                                    }
                                }
                                Toast.makeText(
                                    this@DetailActivity,
                                    "Your itinerary update was successfully.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        if (itinerary != null) {
                            binding.fabDetail.setImageResource(R.drawable.outline_edit_24)
                            binding.fabDetail.setOnClickListener {
                                showAlertDialog(destination = destination, itinerary = itinerary) {
                                    lifecycleScope.launch {
                                        withContext(Dispatchers.IO) {
                                            travelViewModel.updateItinerary(it)
                                        }
                                    }
                                    Toast.makeText(
                                        this@DetailActivity,
                                        "Your itinerary update was successfully.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            binding.fabDetail.setImageResource(R.drawable.outline_add_itinerary_24)
                            binding.fabDetail.setOnClickListener {
                                showAlertDialog(destination = destination) {
                                    lifecycleScope.launch {
                                        withContext(Dispatchers.IO) {
                                            travelViewModel.saveItinerary(it)
                                        }
                                    }
                                    Toast.makeText(
                                        this@DetailActivity,
                                        "Your itinerary save was successfully.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                }
        }
    }

    private fun showAlertDialog(
        destination: DestinationUser.Destination?,
        itinerary: Itinerary? = null,
        onPositive: (Itinerary) -> Unit,
    ) {
        val dialogBinding = CustomDialogBinding.inflate(layoutInflater)
        val alertDialogBuilder = AlertDialog.Builder(this@DetailActivity)
        alertDialogBuilder.setView(dialogBinding.root)

        val dialog = alertDialogBuilder.create()
        dialog.setCancelable(false)
        dialogBinding.textTitle.text =
            if (itinerary == null) "Add Itinerary" else "Edit Itinerary"

        // Date
        val calendar = Calendar.getInstance()
        val yyyy = calendar.get(Calendar.YEAR)
        val mm = calendar.get(Calendar.MONTH)
        val dd = calendar.get(Calendar.DAY_OF_MONTH)
        var getDate: String? = null

        // Date Picker Dialog
        val dateDialog = DatePickerDialog(
            this,
            { _, year, month, day ->
                getDate = "$day/${month + 1}/$year"
                dialogBinding.edtDate.setText(getDate)
            },
            yyyy, mm, dd
        )
        dateDialog.datePicker.minDate = calendar.timeInMillis

        itinerary?.let {
            dialogBinding.edtDate.setText(it.date)
            dialogBinding.edtNotes.setText(it.notes)
            getDate = it.date
        }

        dialogBinding.edtDate.setOnClickListener {
            dateDialog.show()
        }

        dialogBinding.btnNegative.apply {
            icon = ContextCompat.getDrawable(this@DetailActivity, R.drawable.outline_cancel_24)
            text = "CANCEL"
            setOnClickListener {
                dialog.cancel()
            }
        }

        dialogBinding.btnPositive.apply {
            icon = ContextCompat.getDrawable(this@DetailActivity, R.drawable.outline_save_24)
            text = if (itinerary == null) "SAVE" else "UPDATE"
            setOnClickListener {
                val dateText = dialogBinding.edtDate.text.toString()
                val noteText = dialogBinding.edtNotes.text.toString()

                if (dateText.isNotEmpty() && noteText.isNotEmpty()) {
                    val updateItinerary =
                        destination?.let {
                            Itinerary(
                                id = destination.id,
                                name = destination.name,
                                date = getDate.toString(),
                                location = destination.location,
                                notes = noteText,
                                photoUrl = destination.photoUrl,
                                rating = destination.rating,
                                description = destination.description
                            )
                        }
                    if (updateItinerary != null) {
                        onPositive(updateItinerary)
                    }
                    dialog.dismiss()
                    finish()
                } else {
                    Toast.makeText(
                        this@DetailActivity,
                        "Please fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        dialogBinding.btnNegative.apply {
            setOnClickListener {
                dialog.cancel()
            }
        }

        dialogBinding.btnDelete.apply {
            isVisible = itinerary != null
            setOnClickListener {
                lifecycleScope.launch {
                    itinerary?.let {
                        withContext(Dispatchers.IO) {
                            travelViewModel.deleteItinerary(it)
                        }
                    }
                }
                dialog.dismiss().apply {
                    finish()
                    startActivity(Intent(this@DetailActivity, MainActivity::class.java))
                }

                Toast.makeText(
                    this@DetailActivity,
                    "Your itinerary delete was successfully.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        dialogBinding.btnCancel.apply {
            isVisible = itinerary != null
            setOnClickListener {
                dialog.cancel()
                Toast.makeText(
                    this@DetailActivity,
                    "Your itinerary update was cancelled.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    private fun getDetail(data: Any) {
        when (data) {
            is DestinationUser.Destination -> {
                with(binding) {
                    toolbarDetail.title = data.category
                    imageViewPhotoUrl.load(data.photoUrl)
                    tvName.text = data.name
                    tvLocation.text = data.location
                    tvRating.text = String.format(Locale.US, "%.1f", data.rating)
                    tvDescription.text = data.description
                }
            }

            is Itinerary -> {
                with(binding) {
                    imageViewPhotoUrl.load(data.photoUrl)
                    tvName.text = data.name
                    tvLocation.text = data.location
                    tvDescription.text = data.description
                    tvRating.text = String.format(Locale.US, "%.1f", data.rating)
                }
            }
        }
    }

    companion object {
        const val EXTRA_DESTINATION = "extra_destination"
        const val EXTRA_ITINERARY = "extra_itinerary"
    }
}