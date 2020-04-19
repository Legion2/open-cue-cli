package io.github.legion2.open_cue_cli.profile

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import io.github.legion2.open_cue_cli.CliContext
import io.github.legion2.open_cue_cli.model.echoString
import io.github.legion2.open_cue_cli.profileArgument
import kotlinx.coroutines.runBlocking

class TriggerProfile : CliktCommand(name = "trigger") {
    private val profile by profileArgument(help = "Profile that should be triggered as event")
    private val cliContext by requireObject<CliContext>()
    override fun run(): Unit = runBlocking {
        echo(cliContext.sdkClient.trigger(profile).echoString)
    }
}