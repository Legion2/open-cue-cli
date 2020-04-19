package io.github.legion2.open_cue_cli.sdk

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import io.github.legion2.open_cue_cli.CliContext
import kotlinx.coroutines.runBlocking

class DeactivateAllProfiles : CliktCommand(name = "deactivate-all-profiles") {
    private val cliContext by requireObject<CliContext>()
    override fun run(): Unit = runBlocking {
        cliContext.sdkClient.deactivateAll()
    }
}