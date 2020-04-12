package io.github.legion2.open_cue_cli.client

import java.io.FileNotFoundException
import java.io.IOException
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

class LocalFileClient(private val gameSkdEffectsPath: Path = Paths.get("c:\\Program Files (x86)\\Corsair\\CORSAIR iCUE Software\\GameSdkEffects")) {

    fun listAllGames(): Set<String> {
        return gameSkdEffectsPath.toFile().list().orEmpty().toSet()
    }

    fun listProfilesForGame(game: String): Set<String> {
        val inputStream = try {
            gameSkdEffectsPath.resolve(game).resolve("priorities.cfg").toFile().inputStream()
        } catch (e: FileNotFoundException) {
            throw ProfilesConfigFileMissingException("Could not find priorities.cfg file for the game: $game", e)
        }

        val properties = Properties()
        try {
            properties.load(inputStream)
        } catch (e: IOException) {
            throw ProfilesConfigParseException("Could not process priorities.cfg file for the game: $game", e)
        }

        return properties.stringPropertyNames()
    }
}