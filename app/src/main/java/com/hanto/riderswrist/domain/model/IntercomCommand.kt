package com.hanto.riderswrist.domain.model

import com.hanto.riderswrist.domain.common.WearablePath

enum class IntercomCommand {
    CONNECT,        // 연결 요청
    DISCONNECT,     // 연결 해제 요청
    VOLUME_UP,      // 볼륨 업
    VOLUME_DOWN,    // 볼륨 다운
    UNKNOWN;        // 알 수 없는 명령

    companion object {
        // 경로(Path)를 보고 어떤 명령인지 판단하는 함수
        fun fromPath(path: String): IntercomCommand {
            return when (path) {
                WearablePath.CMD_INTERCOM_CONNECT -> CONNECT
                WearablePath.CMD_INTERCOM_DISCONNECT -> DISCONNECT
                WearablePath.CMD_VOLUME_UP -> VOLUME_UP
                WearablePath.CMD_VOLUME_DOWN -> VOLUME_DOWN
                else -> UNKNOWN
            }
        }
    }
}