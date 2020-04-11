package io.github.legion2.open_cue_cli.event

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.arguments.argument
import io.github.legion2.open_cue_cli.client.CueSdkHttpServer
import kotlinx.coroutines.runBlocking

class SetEvent : CliktCommand(name = "set") {
    private val game by argument("game")
    private val event by argument("event", help = "Event that should be set for this game")
    private val sdkClient by requireObject<CueSdkHttpServer>()
    override fun run(): Unit = runBlocking {
        val string = sdkClient.requestString("setevent", mapOf("game" to game, "event" to event))
        echo(string)
    }
}