package io.github.legion2.open_cue_cli.client

import io.github.legion2.open_cue_cli.model.Game
import io.ktor.client.HttpClient
import io.ktor.client.call.typeInfo
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.defaultSerializer
import io.ktor.client.request.get
import io.ktor.http.ParametersBuilder
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import io.ktor.utils.io.core.Input

class CueSdkHttpServer(private val host: String = "localhost", private val port: Int = 25555) {
    val client = HttpClient {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
    }

    fun createURL(function: String, parameters: Map<String, String> = emptyMap()): Url {
        val httpParameters = parameters.entries.fold(ParametersBuilder()) { builder, (key, value) ->
            builder.append(key, value)
            builder
        }
        httpParameters.append("func", function)
        return URLBuilder(host = host, port = port, parameters = httpParameters).path("icue").build()
    }

    suspend fun requestString(function: String, parameters: Map<String, String> = emptyMap()): String {
        val url = createURL(function, parameters)
        val response = try {
            client.get<String>(url)
        } catch (e: Throwable) {
            throw SdkHttpServerException("Can not send request to SDK HTTP Server: $url\n"
                    + "Make sure the SDK HTTP Server is running and this url is correct.", e)
        }
        return translateResponse(response)
    }

    suspend inline fun <reified T> requestJson(function: String, parameters: Map<String, String> = emptyMap()): T {
        val url = createURL(function, parameters)
        val input = try {
            client.get<Input>(url)
        } catch (e: Throwable) {
            throw SdkHttpServerException("Can not send request to SDK HTTP Server: $url\n"
                    + "Make sure the SDK HTTP Server is running and this url is correct.", e)
        }
        return defaultSerializer().read(typeInfo<T>(), input) as T
    }


    private fun translateResponse(response: String): String {
        return when (response) {
            "CE_Success" -> "Success"
            "CE_MissingPrioritiesFile" -> throw GameSdkError(
                    "game or profile not found, check the GameSdkEffects directory if the game and profiles exists.\n" +
                            "Also make sure the priorities.cfg exist and contains all the profiles.")
            else -> throw GameSdkError("Unknown Error, check your input and the logs of the SDK HTTP Server")
        }
    }
}

suspend fun CueSdkHttpServer.currentGame(): Game = requestJson("getgame")

suspend fun CueSdkHttpServer.listGames(): Map<String, Game> = requestJson("getallgames")

suspend fun CueSdkHttpServer.setGame(game: String): String = requestString("setgame", mapOf("game" to game))

suspend fun CueSdkHttpServer.resetGame(game: String): String = requestString("reset", mapOf("game" to game))

suspend fun CueSdkHttpServer.clearAllEvents(game: String): String = requestString("clearallevents", mapOf("game" to game))

suspend fun CueSdkHttpServer.setEvent(game: String, event: String): String = requestString("setevent", mapOf("game" to game, "event" to event))

suspend fun CueSdkHttpServer.clearAllStates(game: String): String = requestString("clearallstates", mapOf("game" to game))

suspend fun CueSdkHttpServer.setState(game: String, state: String): String = requestString("setstate", mapOf("game" to game, "state" to state))

suspend fun CueSdkHttpServer.clearState(game: String, state: String): String = requestString("clearstate", mapOf("game" to game, "state" to state))
