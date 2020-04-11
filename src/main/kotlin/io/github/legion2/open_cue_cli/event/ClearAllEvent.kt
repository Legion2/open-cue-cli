package io.github.legion2.open_cue_cli.event

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.arguments.argument
import io.github.legion2.open_cue_cli.client.CueSdkHttpServer
import kotlinx.coroutines.runBlocking

class ClearAllEvent : CliktCommand(name = "clear-all") {
    private val game by argument("game")
    private val sdkClient by requireObject<CueSdkHttpServer>()
    override fun run(): Unit = runBlocking {
        val string = sdkClient.requestString("clearallevents", mapOf("game" to game))
        echo(string)
    }
}