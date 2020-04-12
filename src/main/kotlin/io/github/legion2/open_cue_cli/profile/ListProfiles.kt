package io.github.legion2.open_cue_cli.profile

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import io.github.legion2.open_cue_cli.CliContext
import io.github.legion2.open_cue_cli.client.currentGame
import io.github.legion2.open_cue_cli.echoString
import io.github.legion2.open_cue_cli.optionForGame
import kotlinx.coroutines.runBlocking

class ListProfiles : CliktCommand("List all available profiles for the current game sorted by priority", name = "list") {
    private val gameOption by optionForGame(help = "List all available profiles for the game given by this option")
    private val priorities by option("-p", "--with-priorities", help = "Show the priority of all profiles").flag()
    private val cliContext by requireObject<CliContext>()
    override fun run() = runBlocking {
        val game = gameOption ?: cliContext.sdkClient.currentGame().name
        val profiles = cliContext.localFileClient.listProfilesForGame(game)
        echo(profiles.sortedBy { it.priority }.echoString { profile -> if (priorities) "${profile.name} ${profile.priority}" else profile.name })
    }
}
