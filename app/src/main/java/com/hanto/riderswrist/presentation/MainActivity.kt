package com.hanto.riderswrist.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hanto.riderswrist.databinding.ActivityMainBinding
import com.hanto.riderswrist.presentation.home.HomeViewModel
import com.hanto.riderswrist.util.applyWindowInsets
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.applyWindowInsets(applyTop = true, applyBottom = true)

        setupObservers()
    }

    private fun setupObservers() {
        // 코루틴 스코프 시작
        lifecycleScope.launch {
            // Lifecycle 상태가 STARTED(화면에 보일 때)일 때만 수집 -> 배터리 절약 및 크래시 방지
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                // 1. 연결 상태 관찰
                launch {
                    viewModel.connectionState.collect { state ->
                        binding.tvConnectionStatus.text = state
                        // 상태에 따른 텍스트 색상 변경 (UX 디테일)
                        if (state.contains("Connected")) {
                            binding.tvConnectionStatus.setTextColor(Color.parseColor("#4CAF50")) // Green
                        } else {
                            binding.tvConnectionStatus.setTextColor(Color.parseColor("#D32F2F")) // Red
                        }
                    }
                }

                // 2. 로그 텍스트 관찰
                launch {
                    viewModel.logText.collect { logs ->
                        binding.tvLogs.text = logs
                    }
                }
            }
        }
    }
}