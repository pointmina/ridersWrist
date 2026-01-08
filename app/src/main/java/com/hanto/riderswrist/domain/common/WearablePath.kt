package com.hanto.riderswrist.domain.common

object WearablePath {
    // [Watch -> Phone] 명령
    const val CMD_INTERCOM_CONNECT = "/cmd/intercom/connect"        // 인터컴 연결해줘
    const val CMD_INTERCOM_DISCONNECT = "/cmd/intercom/disconnect"  // 연결 끊어줘
    const val CMD_VOLUME_UP = "/cmd/volume/up"                      // 볼륨 올려
    const val CMD_VOLUME_DOWN = "/cmd/volume/down"                  // 볼륨 내려

    // [Phone -> Watch] 상태 응답
    const val STATE_CONNECTION = "/state/connection"                // 지금 연결 상태야(true/false)
}