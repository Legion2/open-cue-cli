package io.github.legion2.open_cue_cli.client

import io.github.legion2.open_cue_cli.model.Profile
import io.github.legion2.open_cue_cli.model.SdkDetails
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.ConnectException

class CueSdkHttpServer(private val host: String = "localhost", private val port: Int = 25555) {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
        expectSuccess = true
        HttpResponseValidator {
            handleResponseExceptionWithRequest { exception, _ ->
                when (exception) {
                    is ClientRequestException -> throw GameSdkError(exception.response.body())
                    is ResponseException -> throw SdkHttpServerException(
                        "Server can not process request: ${exception.response.call.request.url}",
                        exception
                    )
                    is ConnectException -> throw SdkHttpServerException(
                        "Can not send request to Open CUE Service\n"
                                + "Make sure the Open CUE Service is running.", exception
                    )
                }
            }
        }
    }

    private fun url(path: String, parameters: Map<String, String> = emptyMap()): Url {
        val httpParameters = parameters.entries.fold(ParametersBuilder()) { builder, (key, value) ->
            builder.append(key, value)
            builder
        }.build()
        return URLBuilder(host = host, port = port, parameters = httpParameters, pathSegments = listOf(path)).build()
    }

    suspend fun currentProfilesDirectoryName(): String = client.get(url("api/sdk/profiles-directory-name")).body()

    suspend fun sdkInfo(): SdkDetails = client.get(url("api/sdk/details")).body()

    suspend fun getControl(): Boolean = client.get(url("api/sdk/control")).body()

    suspend fun setControl(value: Boolean): Boolean = client.put(url("api/sdk/control/${value}")).body()

    suspend fun clearAllEvents(): Unit = client.post(url("api/sdk/stop-all-events")).body()

    suspend fun deactivateAll(): Unit = client.post(url("api/sdk/deactivate-all-profiles")).body()

    suspend fun listProfiles(): List<Profile> = client.get(url("api/profiles")).body()

    suspend fun getProfile(profile: String): Profile = client.get(url("api/profiles/${profile}")).body()

    suspend fun trigger(profile: String): Profile = client.post(url("api/profiles/${profile}/trigger")).body()

    suspend fun activate(profile: String): Profile = client.put(url("api/profiles/${profile}/state/true")).body()

    suspend fun deactivate(profile: String): Profile = client.put(url("api/profiles/${profile}/state/false")).body()
}
