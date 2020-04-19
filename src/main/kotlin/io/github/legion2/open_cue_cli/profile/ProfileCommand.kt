package io.github.legion2.open_cue_cli.profile

import com.github.ajalt.clikt.core.NoOpCliktCommand

class ProfileCommand : NoOpCliktCommand(name = "profile") {
    override fun aliases(): Map<String, List<String>> = mapOf(
            "select" to listOf("activate", "-d")
    )
}
