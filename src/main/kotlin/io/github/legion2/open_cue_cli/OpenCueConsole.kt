package io.github.legion2.open_cue_cli

import com.github.ajalt.clikt.output.CliktConsole
import com.github.ajalt.clikt.output.defaultCliktConsole

class OpenCueConsole : CliktConsole by defaultCliktConsole() {
    override val lineSeparator: String get() = "\n"
}
