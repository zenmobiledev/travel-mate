package com.example.travelmate.presentation.feature.travel.detail

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
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
import com.example.travelmate.presentation.feature.travel.TravelViewModel
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
        if (destination != null) {
            getDetail(destination)
        } else if (itinerary != null) {
            getDetail(itinerary)
        }

        binding.toolbarDetail.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Add itinerary
        binding.fabDetail.setOnClickListener {
            showAlertDialog(destination = destination) {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        travelViewModel.saveItinerary(it)
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
                                photo = destination.photoUrl,
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

        dialogBinding.btnDelete.apply {
            isVisible = itinerary != null
            setOnClickListener {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        itinerary?.let {
                            travelViewModel.deleteItinerary(it)
                        }
                    }
                }
                dialog.dismiss()
            }
        }

        Log.d("DetailActivity", "GET: $itinerary")
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
                    toolbarDetail.title = "Itinerary"
                    binding.textNote.isVisible = true
                    binding.tvNotes.isVisible = true
                    binding.textDate.isVisible = true
                    binding.tvDate.isVisible = true
                    imageViewPhotoUrl.load(data.photo)
                    tvName.text = data.name
                    tvLocation.text = data.location
                    tvDescription.text = data.description
                    tvRating.text = String.format(Locale.US, "%.1f", data.rating)
                    tvDate.text = data.date
                    tvNotes.text = data.notes
                }
            }
        }
    }

    companion object {
        const val EXTRA_DESTINATION = "extra_destination"
        const val EXTRA_ITINERARY = "extra_itinerary"
    }
}