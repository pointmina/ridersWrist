package com.hanto.riderswrist.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanto.riderswrist.domain.common.WearablePath
import com.hanto.riderswrist.domain.usecase.SendIntercomCommandUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WearViewModel @Inject constructor(
    private val sendCommandUseCase: SendIntercomCommandUseCase
) : ViewModel() {

    fun onConnectClicked() {
        sendCommand(WearablePath.CMD_INTERCOM_CONNECT)
    }

    fun onDisconnectClicked() {
        sendCommand(WearablePath.CMD_INTERCOM_DISCONNECT)
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