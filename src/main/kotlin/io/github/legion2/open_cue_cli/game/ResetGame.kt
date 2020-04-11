package io.github.legion2.open_cue_cli.game

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.arguments.argument
import io.github.legion2.open_cue_cli.client.CueSdkHttpServer
import kotlinx.coroutines.runBlocking

class ResetGame : CliktCommand(name = "reset") {
    private val game by argument("game")
    private val sdkClient by requireObject<CueSdkHttpServer>()
    override fun run(): Unit = runBlocking {
        val string = sdkClient.requestString("reset", mapOf("game" to game))
        echo(string)
    }
}