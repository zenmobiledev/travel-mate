package com.example.travelmate.presentation.feature.category

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.travelmate.R
import com.example.travelmate.databinding.ActivityCategoryBinding
import com.example.travelmate.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.buttonStartJourney.setOnClickListener {
            with(binding) {
                val beach = checkBoxBeach.isChecked
                val mountain = checkBoxMountain.isChecked
                val cultural = checkBoxCultural.isChecked
                val culinary = checkBoxCulinary.isChecked
                if (!beach && !mountain && !cultural && !culinary) {
                    // start journey
                    startActivity(Intent(this@CategoryActivity, MainActivity::class.java))
                } else {
                    // save user preferences
                    // start journey
                    Toast.makeText(
                        this@CategoryActivity,
                        "$beach, $mountain, $cultural, $culinary",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this@CategoryActivity, MainActivity::class.java))
                }
            }
        }
    }
}