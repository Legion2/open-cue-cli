package io.github.legion2.open_cue_cli.game

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.arguments.argument
import io.github.legion2.open_cue_cli.CliContext
import io.github.legion2.open_cue_cli.client.resetGame
import kotlinx.coroutines.runBlocking

class ResetGame : CliktCommand(name = "reset") {
    private val game by argument("game")
    private val cliContext by requireObject<CliContext>()
    override fun run(): Unit = runBlocking {
        val string = cliContext.sdkClient.resetGame(game)
        echo(string)
    }
}