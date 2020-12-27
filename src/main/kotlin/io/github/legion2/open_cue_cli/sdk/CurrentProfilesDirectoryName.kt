package io.github.legion2.open_cue_cli.sdk

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import io.github.legion2.open_cue_cli.CliContext
import kotlinx.coroutines.runBlocking

class CurrentProfilesDirectoryName : CliktCommand(name = "profiles-directory-name") {
    private val cliContext by requireObject<CliContext>()
    override fun run() = runBlocking {
        echo(cliContext.sdkClient.currentProfilesDirectoryName())
    }
}