package com.example.travelmate.presentation.feature.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.travelmate.R
import com.example.travelmate.databinding.ActivityLoginBinding
import com.example.travelmate.presentation.feature.category.CategoryActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                lifecycleScope.launch {
                    val token = loginViewModel.getToken()
                    if (token != null) {
                        startActivity(Intent(this@LoginActivity, CategoryActivity::class.java))
                    }
                }
                false
            }
        }
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupObserver()
        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
//                email = "traveller1@gmail.com",
//                password = "abcDEF123!"
            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginViewModel.loginUser(
                    email = email,
                    password = password
                )
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    loginViewModel.isLoading.collect {
                        binding.progressBarLogin.isVisible = it
                    }
                }

                launch {
                    loginViewModel.errorMessage.collect {
                        Toast.makeText(this@LoginActivity, it, Toast.LENGTH_SHORT).show()
                    }
                }

                launch {
                    loginViewModel.token.filterNotNull().collect {
                        if (it.isNotEmpty()) {
                            startActivity(
                                Intent(
                                    this@LoginActivity,
                                    CategoryActivity::class.java
                                )
                            )
                            finish()
                        }
                    }
                }

                launch {
                    loginViewModel.user.filterNotNull().collect {
                        Toast.makeText(this@LoginActivity, "Hi ${it.email}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}
