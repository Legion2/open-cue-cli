package io.github.legion2.open_cue_cli.client

import io.github.legion2.open_cue_cli.model.Profile
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

/**
 * Make a lookup in the local filesystem to get all games and profiles.
 */
class LocalFileClient(private val gameSkdEffectsPath: Path = Paths.get("c:\\Program Files (x86)\\Corsair\\CORSAIR iCUE Software\\GameSdkEffects")) {

    fun listAllGames(): Set<String> {
        return gameSkdEffectsPath.toFile().list().orEmpty().toSet()
    }

    fun listProfilesForGame(game: String): Set<Profile> {
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
        try {
            return properties.map { (name, priority) -> Profile(name as String, (priority as String).toInt()) }.toSet()
        } catch (e: NumberFormatException) {
            throw ProfilesConfigParseException("Could not process priorities.cfg file for the game: $game", e)
        }
    }
}