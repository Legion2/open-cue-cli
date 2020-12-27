package io.github.legion2.open_cue_cli

import com.github.ajalt.clikt.core.subcommands
import io.github.legion2.open_cue_cli.profile.*
import io.github.legion2.open_cue_cli.sdk.*

fun createCLI(): OpenCueCli {
    return OpenCueCli().subcommands(
            SdkCommand().subcommands(
                    SdkInfo(),
                    CurrentProfilesDirectoryName(),
                    SdkControl(),
                    StopAllEvents(),
                    DeactivateAllProfiles()
            ),
            ProfileCommand().subcommands(
                    ListProfiles(),
                    ProfileInfo(),
                    ActivateProfile(),
                    DeactivateProfile(),
                    TriggerProfile()
            ))
}

fun main(args: Array<String>) = createCLI().main(args)
