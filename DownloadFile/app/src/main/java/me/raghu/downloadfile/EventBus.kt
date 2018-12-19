package me.raghu.downloadfile

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.launch


object EventBus {

    private val channel = BroadcastChannel<Any>(1)

    fun send(event: Any, scope: CoroutineScope) {
        scope.launch() {
            channel.send(event)
        }
    }

    fun subscribe(): ReceiveChannel<Any> =
        channel.openSubscription()

    inline fun <reified T> subscribeToEvent() =
        subscribe().filter { it is T }.map { it as T }

}