package io.github.legion2.open_cue_cli.model

data class Profile(val name: String, val priority: Int, val state: Boolean)

val Profile.echoString: String get() = "$name $priority ${if (state) "active" else "not active"}"

