package com.hanto.riderswrist.wear.presentation

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hanto.riderswrist.wear.databinding.ActivityWearMainBinding
import com.hanto.riderswrist.wear.util.applyWindowInsets
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WearMainActivity : ComponentActivity() {

    private lateinit var binding: ActivityWearMainBinding
    private val viewModel: WearViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityWearMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.applyWindowInsets(applyTop = true, applyBottom = true)

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        with(binding) {
            btnConnectionToggle.setOnClickListener {
                viewModel.onConnectionToggleClicked()
            }
            btnVolUp.setOnClickListener { viewModel.onVolumeUp() }
            btnVolDown.setOnClickListener { viewModel.onVolumeDown() }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isConnected.collect { isConnected ->
                    updateConnectionUi(isConnected)
                }
            }
        }
    }

    private fun updateConnectionUi(isConnected: Boolean) {
        with(binding.btnConnectionToggle) {
            isSelected = isConnected
            text = if (isConnected) "DISCONNECT" else "CONNECT"
        }
    }
}