package io.github.legion2.open_cue_cli.state

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.groups.cooccurring
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import io.github.legion2.open_cue_cli.CliContext
import io.github.legion2.open_cue_cli.client.currentGame
import io.github.legion2.open_cue_cli.client.setGame
import io.github.legion2.open_cue_cli.client.setState
import io.github.legion2.open_cue_cli.optionForGame
import kotlinx.coroutines.runBlocking

class SetState : CliktCommand(name = "set") {
    private val optionGroup by GameOptionGroup().cooccurring()
    private val state by argument("state")
    private val cliContext by requireObject<CliContext>()
    override fun run(): Unit = runBlocking {
        val game = optionGroup?.game ?: cliContext.sdkClient.currentGame().name
        if (optionGroup?.setGame == true) {
            cliContext.sdkClient.setGame(game)
        }
        val string = cliContext.sdkClient.setState(game, state)
        echo(string)
    }
}

class GameOptionGroup : OptionGroup() {
    val game by optionForGame().required()
    val setGame by option("-s", "--set-game", help = "Activate that given game before setting the state." +
            " Can only be used with -g.").flag()
}
