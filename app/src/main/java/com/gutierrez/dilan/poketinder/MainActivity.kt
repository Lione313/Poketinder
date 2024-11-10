package com.gutierrez.dilan.poketinder

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.gutierrez.dilan.poketinder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var listPokemon: List<PokemonResponse> = emptyList()
    private val adapter by lazy { PokemonAdapter(listPokemon) }
    private lateinit var binding: ActivityMainBinding
    private val viewModel by lazy { MainViewModel() }

    // Instancia de SharedPreferencesRepository
    private lateinit var sharedPreferencesRepository: SharedPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar SharedPreferencesRepository
        sharedPreferencesRepository = SharedPreferencesRepository()
        sharedPreferencesRepository.setSharedPreference(this)

        // Recuperar el correo del usuario y mostrar un saludo
        val userEmail = sharedPreferencesRepository.getUserEmail()
        if (userEmail.isNotEmpty()) {
            binding.tvWelcome.text = "¡Bienvenido, $userEmail!"
        }

        // Configurar RecyclerView y Observadores
        binding.rvTinderPokemon.adapter = adapter
        observeValues()
    }

    private fun observeValues() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        viewModel.pokemonList.observe(this) { pokemonList ->
            adapter.list = pokemonList
            adapter.notifyDataSetChanged()
        }

        viewModel.errorApi.observe(this) { errorMessage ->
            showMessage(errorMessage)
        }
    }

    private fun showMessage(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}
