package com.gutierrez.dilan.poketinder

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.gutierrez.dilan.poketinder.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        registerViewModel.setSharedPreferences(this)


        registerViewModel.registrationStatus.observe(this, { status ->
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
        })


        binding.btnBackClose.setOnClickListener {
            finish()
        }


        binding.btnRegister.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val confirmPassword = binding.edtConfirmPassword.text.toString()


            registerViewModel.registerUser(email, password, confirmPassword)


            if (registerViewModel.registrationStatus.value == "Registro exitoso") {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    // Método para navegar al login
    fun loginUser(view: android.view.View) {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
