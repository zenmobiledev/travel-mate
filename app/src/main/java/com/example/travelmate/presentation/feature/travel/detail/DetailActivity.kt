package com.example.travelmate.presentation.feature.travel.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil3.load
import com.example.travelmate.R
import com.example.travelmate.databinding.ActivityDetailBinding
import com.example.travelmate.domain.model.destination.DestinationUser
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val destination = intent.getParcelableExtra<DestinationUser.Destination>(EXTRA_DESTINATION)
        if (destination != null) {
            getDetail(destination)
        }
        binding.toolbarDetail.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun getDetail(destination: DestinationUser.Destination) {
        with(binding) {
            toolbarDetail.title = destination.category
            imageViewPhotoUrl.load(destination.photoUrl)
            tvName.text = destination.name
            tvLocation.text = destination.location
            tvRating.text = String.format(Locale.US, "%.1f", destination.rating)
            tvDescription.text = destination.description
        }
    }

    companion object {
        const val EXTRA_DESTINATION = "extra_destination"
    }
}