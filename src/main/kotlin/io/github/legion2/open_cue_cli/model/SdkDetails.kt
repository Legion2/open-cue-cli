package io.github.legion2.open_cue_cli.model

import kotlinx.serialization.Serializable

@Serializable
data class SdkDetails(
    val sdkVersion: String,
    val serverVersion: String,
    val sdkProtocolVersion: Int,
    val serverProtocolVersion: Int,
    val breakingChanges: Boolean,
)
