package io.github.legion2.open_cue_cli.client

import com.github.ajalt.clikt.core.CliktError

class ProfilesConfigParseException(override val message: String?, override val cause: Throwable?) : CliktError()