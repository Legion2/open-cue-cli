package io.github.legion2.open_cue_cli.state

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.arguments.argument
import io.github.legion2.open_cue_cli.CliContext
import io.github.legion2.open_cue_cli.client.setState
import kotlinx.coroutines.runBlocking

class SetState : CliktCommand(name = "set") {
    private val game by argument("game")
    private val state by argument("state")
    private val cliContext by requireObject<CliContext>()
    override fun run(): Unit = runBlocking {
        val string = cliContext.sdkClient.setState(game, state)
        echo(string)
    }
}