package io.github.legion2.open_cue_cli.model

import io.github.legion2.open_cue_cli.echoString

data class Game(val name: String,
                val currentStates: Map<String, Boolean>,
                val lastEventTriggered: String)

val Game.echoString: String
    get() = "$name\nlastEvent: ${lastEventTriggered.ifBlank { "-" }}\ncurrentStates:\n" +
            currentStates.entries.echoString { (key, value) -> "\t$key: $value" }