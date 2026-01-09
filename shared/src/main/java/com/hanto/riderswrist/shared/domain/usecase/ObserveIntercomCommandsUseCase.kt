package com.hanto.riderswrist.shared.domain.usecase

import com.hanto.riderswrist.shared.domain.model.IntercomCommand
import com.hanto.riderswrist.shared.domain.repository.WearableRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveIntercomCommandsUseCase @Inject constructor(
    private val repository: WearableRepository
) {
    operator fun invoke(): Flow<IntercomCommand> {
        return repository.observeIncomingMessages()
            .map { (path, _) ->
                // Repository의 Raw 데이터를 Domain Model로 변환
                IntercomCommand.fromPath(path)
            }
    }
}