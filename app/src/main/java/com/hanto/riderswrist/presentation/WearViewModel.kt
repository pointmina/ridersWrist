package com.hanto.riderswrist.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanto.riderswrist.domain.common.WearablePath
import com.hanto.riderswrist.domain.usecase.SendIntercomCommandUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WearViewModel @Inject constructor(
    private val sendCommandUseCase: SendIntercomCommandUseCase
) : ViewModel() {

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    fun onConnectionToggleClicked() {
        val currentConnectionState = _isConnected.value

        // 토글 로직: 연결되어 있으면 -> 해제 명령, 아니면 -> 연결 명령
        if (currentConnectionState) {
            sendCommand(WearablePath.CMD_INTERCOM_DISCONNECT)
        } else {
            sendCommand(WearablePath.CMD_INTERCOM_CONNECT)
        }

        _isConnected.value = !currentConnectionState
    }

    fun onVolumeUp() {
        sendCommand(WearablePath.CMD_VOLUME_UP)
    }

    fun onVolumeDown() {
        sendCommand(WearablePath.CMD_VOLUME_DOWN)
    }

    private fun sendCommand(path: String) {
        viewModelScope.launch {
            try {
                sendCommandUseCase.execute(path)
                Log.d("WearVM", "Command sent: $path")
            } catch (e: Exception) {
                Log.e("WearVM", "Failed to send command", e)
            }
        }
    }
}