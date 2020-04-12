package io.github.legion2.open_cue_cli.game

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import io.github.legion2.open_cue_cli.CliContext
import io.github.legion2.open_cue_cli.client.currentGame
import io.github.legion2.open_cue_cli.model.echoString
import kotlinx.coroutines.runBlocking

class CurrentGame : CliktCommand(name = "current") {
    private val status by option("-s", "--status", help = "Display the status of the game on the SDK HTTP Server." +
            " This option is enabled by default.").flag("-n", "--only-names", default = true)
    private val cliContext by requireObject<CliContext>()
    override fun run() = runBlocking {
        val game = cliContext.sdkClient.currentGame()
        if (status) {
            echo(game.echoString)
        } else {
            echo(game.name)
        }
    }
}