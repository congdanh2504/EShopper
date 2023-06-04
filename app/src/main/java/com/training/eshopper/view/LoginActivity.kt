package com.training.eshopper.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.training.eshopper.databinding.ActivityLoginBinding
import com.training.eshopper.view.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        observeLoginState()
    }

    private fun initViews() {
        binding.loginButton.setOnClickListener {
            login()
        }
    }

    private fun observeLoginState() {
        authViewModel.loginState.observe(this) { success ->
            if (success) {
                startMainActivity()
            } else {
                showInvalidLoginError()
            }
        }
    }

    private fun startMainActivity() {
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }

    private fun showInvalidLoginError() {
        Toast.makeText(this, "Invalid email or password", Toast.LENGTH_LONG).show()
    }

    private fun login() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        authViewModel.login(email, password)
    }
}