package io.github.legion2.open_cue_cli.sdk

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import io.github.legion2.open_cue_cli.CliContext
import kotlinx.coroutines.runBlocking

class SdkInfo : CliktCommand(name = "info") {
    private val cliContext by requireObject<CliContext>()
    override fun run(): Unit = runBlocking {
        echo(cliContext.sdkClient.sdkInfo())
    }
}