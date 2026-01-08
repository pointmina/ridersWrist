package com.hanto.riderswrist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity // Wear OS는 AppCompatActivity보다 가벼운 ComponentActivity 권장
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hanto.riderswrist.databinding.ActivityMainBinding
import com.hanto.riderswrist.databinding.ActivityWearMainBinding
import com.hanto.riderswrist.util.applyWindowInsets
import dagger.hilt.android.AndroidEntryPoint

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
    }

    private fun setupListeners() {
        with(binding) {
            btnConnect.setOnClickListener { viewModel.onConnectClicked() }
            btnDisconnect.setOnClickListener { viewModel.onDisconnectClicked() }
            btnVolUp.setOnClickListener { viewModel.onVolumeUp() }
            btnVolDown.setOnClickListener { viewModel.onVolumeDown() }
        }
    }
}