package com.hanto.riderswrist.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanto.riderswrist.shared.domain.model.IntercomCommand
import com.hanto.riderswrist.shared.domain.usecase.ObserveIntercomCommandsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observeIntercomCommandsUseCase: ObserveIntercomCommandsUseCase
) : ViewModel() {

    // 1. UI 상태 정의 (화면에 보여줄 텍스트)
    private val _connectionState = MutableStateFlow("Disconnected")
    val connectionState: StateFlow<String> = _connectionState.asStateFlow()

    private val _logText = MutableStateFlow("Ready...")
    val logText: StateFlow<String> = _logText.asStateFlow()

    init {
        // ViewModel 생성 시 바로 감지 시작
        startListening()
    }

    private fun startListening() {
        viewModelScope.launch {
            observeIntercomCommandsUseCase() // UseCase 호출
                .collect { command ->
                    handleCommand(command)
                }
        }
    }

    private fun handleCommand(command: IntercomCommand) {
        // 들어온 명령에 따라 가상의 인터컴 동작 수행
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
            }

            IntercomCommand.VOLUME_DOWN -> {
                updateLog("[$timestamp] Volume DOWN ▼")
            }

            IntercomCommand.UNKNOWN -> {
                // 무시
            }
        }
    }

    // 가짜 연결 로직 (2초 뒤 연결됨)
    private fun simulateConnectionProcess() {
        viewModelScope.launch {
            _connectionState.value = "Connecting..."
            delay(2000) // 2초 대기
            _connectionState.value = "Connected (Mesh 2.0)"
            updateLog("Connection Established!")
        }
    }

    private fun updateLog(msg: String) {
        // 로그가 계속 쌓이도록 처리
        val current = _logText.value
        _logText.value = "$msg\n$current"
    }
}