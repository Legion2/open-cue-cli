package io.github.legion2.open_cue_cli.state

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.options.option
import io.github.legion2.open_cue_cli.CliContext
import io.github.legion2.open_cue_cli.client.clearAllStates
import io.github.legion2.open_cue_cli.client.currentGame
import kotlinx.coroutines.runBlocking

class ClearAllState : CliktCommand(name = "clear-all") {
    private val gameOption by option("-g", "--game", help = "Provide a game as option instead of using the current active game", metavar = "<game>")
    private val cliContext by requireObject<CliContext>()
    override fun run(): Unit = runBlocking {
        val game = gameOption ?: cliContext.sdkClient.currentGame().name
        val string = cliContext.sdkClient.clearAllStates(game)
        echo(string)
    }
}