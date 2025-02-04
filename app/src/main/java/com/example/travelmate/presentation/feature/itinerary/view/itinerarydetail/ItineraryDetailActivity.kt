package com.example.travelmate.presentation.feature.itinerary.view.itinerarydetail

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import com.example.travelmate.databinding.ActivityItineraryDetailBinding
import com.example.travelmate.databinding.EditCustomDialogBinding
import com.example.travelmate.domain.model.destination.Itinerary
import com.example.travelmate.presentation.feature.travel.view.activity.DetailActivity
import com.example.travelmate.utils.convertDate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

@AndroidEntryPoint
class ItineraryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItineraryDetailBinding

    private lateinit var itineraryViewModel: ItineraryDetailViewModel

    private var itinerary: Itinerary? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        itineraryViewModel = ViewModelProvider(this)[ItineraryDetailViewModel::class.java]
        enableEdgeToEdge()
        binding = ActivityItineraryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        itinerary = intent.getParcelableExtra(EXTRA_ITINERARY)

        when {
            itinerary != null -> {
                with(binding) {
                    toolbarItinerary.title = "Itinerary: ${itinerary?.title}"
                    setSupportActionBar(toolbarItinerary)
                    toolbarItinerary.setNavigationOnClickListener {
                        onBackPressedDispatcher.onBackPressed()
                    }
                    tvDate.text = itinerary?.date?.convertDate()
                    tvNote.text = itinerary?.notes
                    imageViewPhotoUrl.load(itinerary?.photoUrl)
                    tvCategory.text = itinerary?.category
                    tvName.text = itinerary?.name
                    tvLocation.text = itinerary?.location
                }

                binding.fabEdit.setOnClickListener {
                    itinerary?.let {
                        showEditDialog(it)
                    }
                }
            }
        }

        binding.outerCard.setOnClickListener {
            startActivity(Intent(this, DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_ITINERARY, itinerary)
            })
        }

        setupObserve()
    }

    private fun showEditDialog(itinerary: Itinerary) {
        val dialogBinding = EditCustomDialogBinding.inflate(layoutInflater)
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(dialogBinding.root)

        val dialog = alertDialogBuilder.create()
        dialog.setCancelable(false)

        dialogBinding.edtDate.setText(itinerary.date)
        dialogBinding.edtTitle.setSelection(itinerary.title.length)
        dialogBinding.edtTitle.setText(itinerary.title)

        dialogBinding.edtNotes.setText(itinerary.notes)

        // Date Picker
        val calendar = Calendar.getInstance()
        val dateDialog = DatePickerDialog(
            this,
            { _, year, month, day ->
                dialogBinding.edtDate.setText("$day/${month + 1}/$year")
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        dialogBinding.edtDate.setOnClickListener { dateDialog.show() }

        dialogBinding.btnPositive.setOnClickListener {
            val updatedItinerary = itinerary.copy(
                date = dialogBinding.edtDate.text.toString(),
                title = dialogBinding.edtTitle.text.toString(),
                notes = dialogBinding.edtNotes.text.toString()
            )

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    itineraryViewModel.updateItinerary(updatedItinerary)
                }
            }
            Toast.makeText(this, "Itinerary updated successfully", Toast.LENGTH_SHORT).show()
            dialog.dismiss().also {
                itineraryViewModel.getItinerary()
            }
        }

        dialogBinding.btnNegative.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    itineraryViewModel.deleteItinerary(itinerary)
                }
            }
            Toast.makeText(this, "Itinerary deleted successfully", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
            finish()
        }

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    private fun setupObserve() {
        lifecycleScope.launch {
            itineraryViewModel.itinerary.collect {
                if (it != null) {
                    with(binding) {
                        toolbarItinerary.title = "Itinerary: ${it.title}"
                        tvDate.text = it.date.convertDate()
                        tvNote.text = it.notes
                    }
                    itinerary = it
                }
            }
        }
    }

    companion object {
        const val EXTRA_ITINERARY = "extra_itinerary"
    }
}