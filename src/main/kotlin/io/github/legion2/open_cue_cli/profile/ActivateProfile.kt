package io.github.legion2.open_cue_cli.profile

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import io.github.legion2.open_cue_cli.CliContext
import io.github.legion2.open_cue_cli.model.echoString
import io.github.legion2.open_cue_cli.profileArgument
import kotlinx.coroutines.runBlocking

class ActivateProfile : CliktCommand(name = "activate") {
    private val disableOther by option("-d", "--disable-other", help = "Deactivate all profiles before activating this profile").flag()
    private val profile by profileArgument()
    private val cliContext by requireObject<CliContext>()
    override fun run(): Unit = runBlocking {
        if (disableOther) {
            cliContext.sdkClient.deactivateAll()
        }
        echo(cliContext.sdkClient.activate(profile).echoString)
    }
}
