package io.github.legion2.open_cue_cli.game

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import io.github.legion2.open_cue_cli.CliContext
import io.github.legion2.open_cue_cli.client.listGames
import io.github.legion2.open_cue_cli.echoString
import io.github.legion2.open_cue_cli.model.Game
import io.github.legion2.open_cue_cli.model.echoString
import kotlinx.coroutines.runBlocking

class ListGames : CliktCommand(name = "list") {
    private val status by option("-s", "--status", help = "Display the status of the games on the SDK HTTP Server")
            .flag("-n", "--only-names", default = false)
    private val all by option("-a", "--all", help = "Display all available games, by looking into the GameSdkEffects directory." +
            " This option is enabled by default. If disabled only the games known by the CUE SDK HTTP Server are listed.")
            .flag("--only-sdk-server", default = true)
    private val cliContext by requireObject<CliContext>()
    override fun run() = runBlocking {
        val games = if (all) {
            cliContext.localFileClient.listAllGames().map { name -> name to Game(name, emptyMap(), "") }.toMap().toMutableMap()
        } else {
            mutableMapOf()
        }
        games.putAll(cliContext.sdkClient.listGames())

        if (status) {
            echo(games.values.sortedBy { it.name }.echoString { it.echoString })
        } else {
            echo(games.keys.sorted().echoString)
        }
    }
}