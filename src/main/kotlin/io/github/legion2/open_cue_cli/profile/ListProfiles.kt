package io.github.legion2.open_cue_cli.profile

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import io.github.legion2.open_cue_cli.CliContext
import io.github.legion2.open_cue_cli.echoString
import io.github.legion2.open_cue_cli.model.echoString
import kotlinx.coroutines.runBlocking

class ListProfiles : CliktCommand("List all available profiles for the current game sorted by priority", name = "list") {
    private val details by option("-d", "--with-details", help = "Show the priority and state of all profiles").flag()
    private val activeProfiles by option("-a", "--active", help = "Only list active profiles").flag()
    private val cliContext by requireObject<CliContext>()
    override fun run() = runBlocking {
        val profiles = cliContext.sdkClient.listProfiles().run {
            if (activeProfiles) filter { profile -> profile.state } else this
        }
        echo(profiles.sortedBy { it.priority }.echoString { profile -> if (details) profile.echoString else profile.name }, lineSeparator = "\n")
    }
}
