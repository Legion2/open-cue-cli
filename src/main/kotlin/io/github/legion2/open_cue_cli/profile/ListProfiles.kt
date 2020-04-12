package io.github.legion2.open_cue_cli.profile

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.options.option
import io.github.legion2.open_cue_cli.CliContext
import io.github.legion2.open_cue_cli.client.currentGame
import io.github.legion2.open_cue_cli.echoString
import kotlinx.coroutines.runBlocking

class ListProfiles : CliktCommand("List all available profiles for the current game", name = "list") {
    private val gameOption by option("-g", "--game", help = "List all available profiles for the game given by this option")
    private val cliContext by requireObject<CliContext>()
    override fun run() = runBlocking {
        val game = gameOption ?: cliContext.sdkClient.currentGame().name
        val profiles = cliContext.localFileClient.listProfilesForGame(game)
        echo(profiles.sorted().echoString)
    }
}
