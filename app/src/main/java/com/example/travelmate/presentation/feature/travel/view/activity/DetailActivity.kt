package com.example.travelmate.presentation.feature.travel.view.activity

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil3.load
import com.example.travelmate.R
import com.example.travelmate.databinding.ActivityDetailBinding
import com.example.travelmate.databinding.AddCustomDialogBinding
import com.example.travelmate.domain.model.destination.DestinationUser
import com.example.travelmate.domain.model.destination.Itinerary
import com.example.travelmate.presentation.feature.travel.viewmodel.TravelViewModel
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

        binding.toolbarDetail.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val destination = intent.getParcelableExtra<DestinationUser.Destination>(EXTRA_DESTINATION)
        val itinerary = intent.getParcelableExtra<Itinerary>(EXTRA_ITINERARY)
        when {
            destination != null -> {
                with(binding) {
                    toolbarDetail.title = destination.category
                    imageViewPhotoUrl.load(destination.photoUrl)
                    tvName.text = destination.name
                    tvLocation.text = destination.location
                    tvRating.text = String.format(Locale.US, "%.1f", destination.rating)
                    tvDescription.text = destination.description
                }
            }

            itinerary != null -> {
                with(binding) {
                    toolbarDetail.title = itinerary.category
                    imageViewPhotoUrl.load(itinerary.photoUrl)
                    tvName.text = itinerary.name
                    tvLocation.text = itinerary.location
                    tvRating.text = String.format(Locale.US, "%.1f", itinerary.rating)
                    tvDescription.text = itinerary.description
                }
            }
        }
        if (destination != null) {
            binding.fabAdd.setOnClickListener {
                Log.d("TAG", "Add fab on clicked!")
                showAlertDialog(destination)
            }
        }
        if (itinerary != null) {
            binding.fabAdd.setOnClickListener {
                Log.d("TAG", "Add fab on clicked!")
                showAlertDialog(
                    destination = DestinationUser.Destination(
                        category = itinerary.category,
                        name = itinerary.name,
                        photoUrl = itinerary.photoUrl,
                        description = itinerary.description.toString(),
                        location = itinerary.location,
                        rating = itinerary.rating,
                        id = itinerary.id,
                        date = itinerary.date,
                        title = itinerary.title,
                        notes = itinerary.notes
                    )
                )
            }
        }
    }

    private fun showAlertDialog(destination: DestinationUser.Destination) {
        val dialogBinding = AddCustomDialogBinding.inflate(layoutInflater)
        val alertDialogBuilder = AlertDialog.Builder(this)
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

        dialogBinding.btnPositive.apply {
            setOnClickListener {
                val dateText = dialogBinding.edtDate.text.toString()
                val titleText = dialogBinding.edtTitle.text.toString()
                val noteText = dialogBinding.edtNotes.text.toString()

                if (dateText.isNotEmpty() && titleText.isNotEmpty() && noteText.isNotEmpty()) {
                    val itinerary = Itinerary(
                        id = 0,
                        category = destination.category,
                        name = destination.name,
                        photoUrl = destination.photoUrl,
                        description = destination.description,
                        location = destination.location,
                        rating = destination.rating,
                        date = getDate.toString(),
                        title = titleText,
                        notes = noteText
                    )

                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            travelViewModel.saveItinerary(itinerary)
                        }
                    }
                    Toast.makeText(
                        this@DetailActivity,
                        "Your itinerary save was successfully.",
                        Toast.LENGTH_SHORT
                    ).show().also {
                        dialog.dismiss()
                        finish()
                    }
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

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    companion object {
        const val EXTRA_DESTINATION = "extra_destination"
        const val EXTRA_ITINERARY = "extra_itinerary"
    }
}