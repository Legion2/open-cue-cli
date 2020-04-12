package io.github.legion2.open_cue_cli.state

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import io.github.legion2.open_cue_cli.CliContext
import io.github.legion2.open_cue_cli.client.clearAllStates
import io.github.legion2.open_cue_cli.client.currentGame
import io.github.legion2.open_cue_cli.optionForGame
import kotlinx.coroutines.runBlocking

class ClearAllState : CliktCommand(name = "clear-all") {
    private val gameOption by optionForGame()
    private val cliContext by requireObject<CliContext>()
    override fun run(): Unit = runBlocking {
        val game = gameOption ?: cliContext.sdkClient.currentGame().name
        val string = cliContext.sdkClient.clearAllStates(game)
        echo(string)
    }
}