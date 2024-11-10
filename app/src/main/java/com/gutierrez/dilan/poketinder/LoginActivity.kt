package com.gutierrez.dilan.poketinder

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gutierrez.dilan.poketinder.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        loginViewModel = LoginViewModel(this)

        observeValues()
    }

    private fun observeValues() {
        loginViewModel.inputsError.observe(this) {
            Toast.makeText(this, "Ingrese los datos completos", Toast.LENGTH_SHORT).show()
        }

        loginViewModel.authError.observe(this) {
            Toast.makeText(this, "Error usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }

        loginViewModel.registerError.observe(this) {
            Toast.makeText(this, "Usuario no registrado", Toast.LENGTH_SHORT).show()
        }

        loginViewModel.loginSuccess.observe(this) {

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()


            if (validateEmail(email) && validatePassword(password)) {
                loginViewModel.validateInputs(email, password)
            } else {
                Toast.makeText(this, "Por favor ingrese datos válidos", Toast.LENGTH_SHORT).show()
            }
        }


        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }


    private fun validateEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


    private fun validatePassword(password: String): Boolean {
        return password.isNotEmpty() && password.length >= 8
    }
}
