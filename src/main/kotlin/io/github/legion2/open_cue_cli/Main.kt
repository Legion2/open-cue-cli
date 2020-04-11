package io.github.legion2.open_cue_cli

import com.github.ajalt.clikt.core.subcommands
import io.github.legion2.open_cue_cli.event.ClearAllEvent
import io.github.legion2.open_cue_cli.event.EventCommand
import io.github.legion2.open_cue_cli.event.SetEvent
import io.github.legion2.open_cue_cli.game.*
import io.github.legion2.open_cue_cli.state.ClearAllState
import io.github.legion2.open_cue_cli.state.ClearState
import io.github.legion2.open_cue_cli.state.SetState
import io.github.legion2.open_cue_cli.state.StateCommand

fun createCLI(): OpenCueCli {
    return OpenCueCli().subcommands(
            GameCommand().subcommands(
                    CurrentGame(),
                    ListGames(),
                    SetGame(),
                    ResetGame()
            ),
            StateCommand().subcommands(
                    SetState(),
                    ClearState(),
                    ClearAllState()
            ),
            EventCommand().subcommands(
                    SetEvent(),
                    ClearAllEvent()
            ))
}

fun main(args: Array<String>) = createCLI().main(args)
