package io.github.legion2.open_cue_cli

import com.github.ajalt.clikt.completion.CompletionCandidates
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.versionOption
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.clikt.parameters.types.restrictTo
import io.github.legion2.open_cue_cli.CliContext.Companion.createCliContext

class OpenCueCli : CliktCommand(name = "open-cue-cli", autoCompleteEnvvar = "OPEN_CUE_CLI_COMPLETE") {
    private val port: Int by option("-p", "--port", help = "Port of the Open CUE Service", metavar = "<port>")
            .int().restrictTo(min = 0).default(25555)
    private val host: String by option("-H", "--host", help = "Hostname or ip address of the Open CUE Service",
            metavar = "<host or ip>").default("localhost")

    init {
        versionOption("0.3.0")
    }

    override fun run() {
        currentContext.findOrSetObject { createCliContext(host, port) }
    }
}

val <T> Iterable<T>.echoString: String get() = joinToString("\n")

fun <T> Iterable<T>.echoString(transform: (T) -> CharSequence): String = joinToString("\n", transform = transform)

fun CliktCommand.profileArgument(help: String? = null) = argument("profile", help = help ?: "The name of the profile",
        completionCandidates = CompletionCandidates.Custom.fromStdout("(test -x ./open-cue-cli && ./open-cue-cli profile list) || (hash open-cue-cli 2>/dev/null && open-cue-cli profile list)"))
