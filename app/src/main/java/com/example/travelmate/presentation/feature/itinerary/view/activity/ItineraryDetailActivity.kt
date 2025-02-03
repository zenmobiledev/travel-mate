package com.example.travelmate.presentation.feature.itinerary.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil3.load
import com.example.travelmate.R
import com.example.travelmate.databinding.ActivityItineraryDetailBinding
import com.example.travelmate.domain.model.destination.Itinerary
import com.example.travelmate.presentation.feature.travel.view.activity.DetailActivity

class ItineraryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItineraryDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityItineraryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val itinerary = intent.getParcelableExtra<Itinerary>(EXTRA_ITINERARY)
        if (itinerary != null) getDetail(itinerary)


        binding.outerCard.setOnClickListener {
            startActivity(Intent(this, DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_ITINERARY, itinerary)
            })
        }
    }

    private fun getDetail(itinerary: Itinerary) {
        with(binding) {
            toolbarItinerary.title = "Itinerary: ${itinerary.name}"
            setSupportActionBar(toolbarItinerary)
            toolbarItinerary.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            tvDate.text = itinerary.date
            tvNote.text = itinerary.notes
            imageViewPhotoUrl.load(itinerary.photoUrl)
            tvName.text = itinerary.name
            tvLocation.text = itinerary.location
        }
    }

    companion object {
        const val EXTRA_ITINERARY = "extra_itinerary"
    }
}