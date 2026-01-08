package com.hanto.riderswrist.domain.repository

import kotlinx.coroutines.flow.Flow

interface WearableRepository {
    // 메시지 보내기 (단방향: Fire-and-forget)
    // payload: 추가 정보가 필요하면 바이트 배열로 보냄
    suspend fun sendMessage(path: String, payload: ByteArray? = null)

    // 들어오는 메시지 관찰하기 (Flow: 실시간 스트림)
    // Pair<경로, 데이터> 형태로 반환
    fun observeIncomingMessages(): Flow<Pair<String, ByteArray?>>
}