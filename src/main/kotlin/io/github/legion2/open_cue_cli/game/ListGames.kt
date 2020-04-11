package io.github.legion2.open_cue_cli.game

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import io.github.legion2.open_cue_cli.client.CueSdkHttpServer
import io.github.legion2.open_cue_cli.echoString
import io.github.legion2.open_cue_cli.model.Game
import io.github.legion2.open_cue_cli.model.echoString
import kotlinx.coroutines.runBlocking

class ListGames : CliktCommand(name = "list") {
    private val namesOnly by option("--only-names", "-n").flag()
    private val sdkClient by requireObject<CueSdkHttpServer>()
    override fun run() = runBlocking {
        val games = sdkClient.requestJson<Map<String, Game>>("getallgames")
        if (namesOnly) {
            echo(games.keys.echoString)
        } else {
            echo(games.values.echoString { it.echoString })
        }
    }
}