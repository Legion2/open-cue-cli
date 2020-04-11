package io.github.legion2.open_cue_cli.state

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.arguments.argument
import io.github.legion2.open_cue_cli.client.CueSdkHttpServer
import kotlinx.coroutines.runBlocking

class ClearState : CliktCommand(name = "clear") {
    private val game by argument("game")
    private val state by argument("state")
    private val sdkClient by requireObject<CueSdkHttpServer>()
    override fun run(): Unit = runBlocking {
        val string = sdkClient.requestString("clearstate", mapOf("game" to game, "state" to state))
        echo(string)
    }
}