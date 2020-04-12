package io.github.legion2.open_cue_cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.versionOption
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.clikt.parameters.types.restrictTo
import io.github.legion2.open_cue_cli.CliContext.Companion.createCliContext

class OpenCueCli : CliktCommand(name = "open-cue-cli") {
    private val port: Int by option("-p", "--port", help = "Port of the iCUE SDK HTTP Server").int().restrictTo(min = 0).default(25555)
    private val host: String by option("-H", "--host", help = "Hostname or ip address of the iCUE SDK HTTP Server").default("localhost")

    init {
        versionOption("0.1.0")
    }

    override fun run() {
        currentContext.findOrSetObject { createCliContext(host, port) }
    }
}

val <T> Iterable<T>.echoString: String get() = joinToString("\n")

fun <T> Iterable<T>.echoString(transform: (T) -> CharSequence): String = joinToString("\n", transform = transform)
