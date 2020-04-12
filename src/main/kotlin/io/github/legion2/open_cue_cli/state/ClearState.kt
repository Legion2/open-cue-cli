package io.github.legion2.open_cue_cli.state

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.option
import io.github.legion2.open_cue_cli.CliContext
import io.github.legion2.open_cue_cli.client.clearState
import io.github.legion2.open_cue_cli.client.currentGame
import kotlinx.coroutines.runBlocking

class ClearState : CliktCommand(name = "clear") {
    private val gameOption by option("-g", "--game", help = "Provide a game as option instead of using the current active game", metavar = "<game>")
    private val state by argument("state")
    private val cliContext by requireObject<CliContext>()
    override fun run(): Unit = runBlocking {
        val game = gameOption ?: cliContext.sdkClient.currentGame().name
        val string = cliContext.sdkClient.clearState(game, state)
        echo(string)
    }
}