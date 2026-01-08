package com.hanto.riderswrist.domain.usecase

import com.hanto.riderswrist.domain.common.WearablePath
import com.hanto.riderswrist.domain.repository.WearableRepository
import javax.inject.Inject

class SendIntercomCommandUseCase @Inject constructor(
    private val repository: WearableRepository
) {
    // Enum이나 타입에 따라 적절한 Path로 메시지를 쏘는 역할
    suspend fun execute(commandPath: String) {
        repository.sendMessage(commandPath, null)
    }
}