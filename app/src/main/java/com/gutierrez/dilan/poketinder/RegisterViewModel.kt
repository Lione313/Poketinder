package com.gutierrez.dilan.poketinder

import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {

    val registrationStatus = MutableLiveData<String>()
    private val sharedPreferencesRepository = SharedPreferencesRepository()

    fun setSharedPreferences(context: RegisterActivity) {
        sharedPreferencesRepository.setSharedPreference(context)
    }

    fun registerUser(email: String, password: String, confirmPassword: String) {
        if (validateEmail(email) && validatePassword(password, confirmPassword)) {
            sharedPreferencesRepository.saveUserEmail(email)
            sharedPreferencesRepository.saveUserPassword(password)
            registrationStatus.value = "Registro exitoso"
        }
    }

    private fun validateEmail(email: String): Boolean {
        if (email.isEmpty()) {
            registrationStatus.value = "Por favor ingrese un correo electronico"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            registrationStatus.value = "Correo electronico no valido"
            return false
        }
        return true
    }

    private fun validatePassword(password: String, confirmPassword: String): Boolean {
        if (password.length < 8) {
            registrationStatus.value = "La contraseña debe tener al menos 8 caracteres"
            return false
        }
        if (password != confirmPassword) {
            registrationStatus.value = "Las contraseñas no coinciden"
            return false
        }
        return true
    }
}
