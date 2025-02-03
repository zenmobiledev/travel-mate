package com.example.travelmate.presentation.feature.category

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.travelmate.R
import com.example.travelmate.databinding.ActivityCategoryBinding
import com.example.travelmate.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]

        enableEdgeToEdge()
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            val category = categoryViewModel.getCategory()
            if (category.values.any { it }) {
                navigateToMainActivity()
            }
        }

        // check if any checkbox is checked
        updateStartJourneyButtonState()

        // update button state when checkbox is checked
        with(binding) {
            checkBoxBeach.setOnCheckedChangeListener { _, _ -> updateStartJourneyButtonState() }
            checkBoxMountain.setOnCheckedChangeListener { _, _ -> updateStartJourneyButtonState() }
            checkBoxCultural.setOnCheckedChangeListener { _, _ -> updateStartJourneyButtonState() }
            checkBoxCulinary.setOnCheckedChangeListener { _, _ -> updateStartJourneyButtonState() }
        }

        // save category when button is clicked
        with(binding) {
            buttonStartJourney.setOnClickListener {
                val categories = listOf(
                    checkBoxBeach.isChecked.also { binding.checkBoxBeach.text = "Beach" },
                    checkBoxMountain.isChecked.also { binding.checkBoxMountain.text = "Mountain" },
                    checkBoxCultural.isChecked.also { binding.checkBoxCultural.text = "Cultural" },
                    checkBoxCulinary.isChecked.also { binding.checkBoxCulinary.text = "Culinary" }
                )
                val (beach, mountain, cultural, culinary) = categories
                if (!beach && !mountain && !cultural && !culinary) {
                    buttonStartJourney.isEnabled = false
                } else {
                    lifecycleScope.launch {
                        categoryViewModel.saveCategory(beach, mountain, cultural, culinary)
                    }
                    navigateToMainActivity()
                }
            }
        }
    }

    // check if any checkbox is checked
    private fun updateStartJourneyButtonState() {
        val isAnyChecked = binding.checkBoxBeach.isChecked ||
                binding.checkBoxMountain.isChecked ||
                binding.checkBoxCultural.isChecked ||
                binding.checkBoxCulinary.isChecked

        binding.buttonStartJourney.isEnabled = isAnyChecked
    }

    // navigate to main activity
    private fun navigateToMainActivity() {
        val intent = Intent(this@CategoryActivity, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }
}