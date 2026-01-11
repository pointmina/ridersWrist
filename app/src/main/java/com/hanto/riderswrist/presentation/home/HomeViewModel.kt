package com.hanto.riderswrist.presentation.home

import android.content.Context
import android.media.AudioManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanto.riderswrist.shared.domain.model.IntercomCommand
import com.hanto.riderswrist.shared.domain.usecase.ObserveIntercomCommandsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val observeIntercomCommandsUseCase: ObserveIntercomCommandsUseCase
) : ViewModel() {

    // 1. UI 상태 정의
    private val _connectionState = MutableStateFlow("Disconnected")
    val connectionState: StateFlow<String> = _connectionState.asStateFlow()

    private val _logText = MutableStateFlow("Ready...")
    val logText: StateFlow<String> = _logText.asStateFlow()

    // 오디오 매니저
    private val audioManager: AudioManager by lazy {
        context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    init {
        startListening()
    }

    private fun startListening() {
        viewModelScope.launch {
            observeIntercomCommandsUseCase()
                .collect { command ->
                    handleCommand(command)
                }
        }
    }

    private fun handleCommand(command: IntercomCommand) {
        val timestamp = System.currentTimeMillis()

        when (command) {
            IntercomCommand.CONNECT -> {
                updateLog("[$timestamp] Connecting...")
                simulateConnectionProcess()
            }
            IntercomCommand.DISCONNECT -> {
                _connectionState.value = "Disconnected"
                updateLog("[$timestamp] Disconnected")
            }
            IntercomCommand.VOLUME_UP -> {
                updateLog("[$timestamp] Volume UP ▲")
                adjustVolume(AudioManager.ADJUST_RAISE)
            }
            IntercomCommand.VOLUME_DOWN -> {
                updateLog("[$timestamp] Volume DOWN ▼")
                adjustVolume(AudioManager.ADJUST_LOWER)
            }
            IntercomCommand.UNKNOWN -> {
                // 무시
            }
        }
    }

    // 🔊 실제 시스템 볼륨 조절 함수
    private fun adjustVolume(direction: Int) {
        try {
            audioManager.adjustStreamVolume(
                AudioManager.STREAM_MUSIC, // 미디어 볼륨 조절
                direction,                 // RAISE or LOWER
                AudioManager.FLAG_SHOW_UI  // 폰 화면에 볼륨바 표시 (피드백)
            )
        } catch (e: Exception) {
            updateLog("Volume Error: ${e.message}")
        }
    }

    // 가짜 연결 로직 (2초 뒤 연결됨)
    private fun simulateConnectionProcess() {
        viewModelScope.launch {
            _connectionState.value = "Connecting..."
            delay(2000)
            _connectionState.value = "Connected (Mesh 2.0)"
            updateLog("Connection Established!")
        }
    }

    private fun updateLog(msg: String) {
        _logText.value = "$msg\n${_logText.value}"
    }
}