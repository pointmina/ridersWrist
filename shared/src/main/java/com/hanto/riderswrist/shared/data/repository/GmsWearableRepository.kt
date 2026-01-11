package com.hanto.riderswrist.shared.data.repository

import android.content.Context
import android.util.Log
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.Wearable
import com.hanto.riderswrist.shared.domain.repository.WearableRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GmsWearableRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : WearableRepository {

    private val messageClient: MessageClient by lazy { Wearable.getMessageClient(context) }
    private val nodeClient by lazy { Wearable.getNodeClient(context) }

    override suspend fun sendMessage(path: String, payload: ByteArray?) {
        try {
            val nodes = nodeClient.connectedNodes.await()
            nodes.forEach { node ->
                messageClient.sendMessage(node.id, path, payload).await()
                Log.d("GmsWearableRepo", "Sent to ${node.displayName}: $path")
            }
        } catch (e: Exception) {
            Log.e("GmsWearableRepo", "Failed to send message", e)
        }
    }

    override fun observeIncomingMessages(): Flow<Pair<String, ByteArray?>> = callbackFlow {
        val listener = MessageClient.OnMessageReceivedListener { messageEvent ->
            Log.d("GmsRepo", "Received path: ${messageEvent.path} from ${messageEvent.sourceNodeId}")
            trySend(messageEvent.path to messageEvent.data)
        }
        messageClient.addListener(listener)
        awaitClose { messageClient.removeListener(listener) }
    }
}