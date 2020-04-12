package io.github.legion2.open_cue_cli.state

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.arguments.argument
import io.github.legion2.open_cue_cli.CliContext
import io.github.legion2.open_cue_cli.client.clearState
import io.github.legion2.open_cue_cli.client.currentGame
import io.github.legion2.open_cue_cli.optionForGame
import kotlinx.coroutines.runBlocking

class ClearState : CliktCommand(name = "clear") {
    private val gameOption by optionForGame()
    private val state by argument("state")
    private val cliContext by requireObject<CliContext>()
    override fun run(): Unit = runBlocking {
        val game = gameOption ?: cliContext.sdkClient.currentGame().name
        val string = cliContext.sdkClient.clearState(game, state)
        echo(string)
    }
}