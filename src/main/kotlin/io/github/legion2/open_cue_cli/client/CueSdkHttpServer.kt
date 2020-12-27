package io.github.legion2.open_cue_cli.client

import com.google.gson.Gson
import io.github.legion2.open_cue_cli.model.Profile
import io.github.legion2.open_cue_cli.model.SdkDetails
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.HttpResponseValidator
import io.ktor.client.features.ResponseException
import io.ktor.client.features.addDefaultResponseValidation
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.statement.readText
import io.ktor.http.ParametersBuilder
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import java.net.ConnectException

class CueSdkHttpServer(private val host: String = "localhost", private val port: Int = 25555) {
    private val client = HttpClient {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
        addDefaultResponseValidation()
        HttpResponseValidator {
            handleResponseException { exception ->

                when (exception) {
                    is ClientRequestException -> throw GameSdkError(Gson().fromJson(exception.response.readText(), String::class.java))
                    is ResponseException -> throw SdkHttpServerException("Server can not process request: ${exception.response.call.request.url}", exception)
                    is ConnectException -> throw SdkHttpServerException("Can not send request to Open CUE Service\n"
                            + "Make sure the Open CUE Service is running.", exception)
                }
            }
        }
    }

    private fun url(path: String, parameters: Map<String, String> = emptyMap()): Url {
        val httpParameters = parameters.entries.fold(ParametersBuilder()) { builder, (key, value) ->
            builder.append(key, value)
            builder
        }
        return URLBuilder(host = host, port = port, parameters = httpParameters).path(path).build()
    }

    suspend fun currentProfilesDirectoryName(): String = client.get(url("api/sdk/profiles-directory-name"))

    suspend fun sdkInfo(): SdkDetails = client.get(url("api/sdk/details"))

    suspend fun getControl(): Boolean = client.get(url("api/sdk/control"))

    suspend fun setControl(value: Boolean): Boolean = client.put(url("api/sdk/control/${value}"))

    suspend fun clearAllEvents(): Unit = client.post(url("api/sdk/stop-all-events"))

    suspend fun deactivateAll(): Unit = client.post(url("api/sdk/deactivate-all-profiles"))

    suspend fun listProfiles(): List<Profile> = client.get(url("api/profiles"))

    suspend fun getProfile(profile: String): Profile = client.get(url("api/profiles/${profile}"))

    suspend fun trigger(profile: String): Profile = client.post(url("api/profiles/${profile}/trigger"))

    suspend fun activate(profile: String): Profile = client.put(url("api/profiles/${profile}/state/true"))

    suspend fun deactivate(profile: String): Profile = client.put(url("api/profiles/${profile}/state/false"))
}
