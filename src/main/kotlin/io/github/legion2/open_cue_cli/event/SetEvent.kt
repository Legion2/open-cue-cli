package io.github.legion2.open_cue_cli.event

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.arguments.argument
import io.github.legion2.open_cue_cli.CliContext
import io.github.legion2.open_cue_cli.client.currentGame
import io.github.legion2.open_cue_cli.client.setEvent
import io.github.legion2.open_cue_cli.optionForGame
import kotlinx.coroutines.runBlocking

class SetEvent : CliktCommand(name = "set") {
    private val gameOption by optionForGame()
    private val event by argument("event", help = "Event that should be set for this game")
    private val cliContext by requireObject<CliContext>()
    override fun run(): Unit = runBlocking {
        val game = gameOption ?: cliContext.sdkClient.currentGame().name
        val string = cliContext.sdkClient.setEvent(game, event)
        echo(string)
    }
}