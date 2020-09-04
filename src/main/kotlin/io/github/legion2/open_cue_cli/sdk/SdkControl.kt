package io.github.legion2.open_cue_cli.sdk

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.switch
import io.github.legion2.open_cue_cli.CliContext
import kotlinx.coroutines.runBlocking

class SdkControl : CliktCommand(name = "control") {
    private val value by option(help = "request or release the control of the sdk").switch("--request" to true, "--release" to false)
    private val cliContext by requireObject<CliContext>()
    override fun run(): Unit = runBlocking {
        val value = value
        if (value == null) {
            echo(cliContext.sdkClient.getControl())
        } else {
            echo(cliContext.sdkClient.setControl(value))
        }
    }
}