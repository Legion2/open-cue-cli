package io.github.legion2.open_cue_cli.game

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import io.github.legion2.open_cue_cli.client.CueSdkHttpServer
import io.github.legion2.open_cue_cli.model.Game
import io.github.legion2.open_cue_cli.model.echoString
import kotlinx.coroutines.runBlocking

class CurrentGame : CliktCommand(name = "current") {
    private val sdkClient by requireObject<CueSdkHttpServer>()
    override fun run() = runBlocking {
        val game = sdkClient.requestJson<Game>("getgame")
        echo(game.echoString)
    }
}