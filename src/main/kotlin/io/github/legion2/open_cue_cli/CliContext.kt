package io.github.legion2.open_cue_cli

import io.github.legion2.open_cue_cli.client.CueSdkHttpServer
import io.github.legion2.open_cue_cli.client.LocalFileClient

class CliContext(
        val sdkClient: CueSdkHttpServer,
        val localFileClient: LocalFileClient) {
    companion object {
        fun createCliContext(host: String, port: Int): CliContext {
            return CliContext(CueSdkHttpServer(host, port), LocalFileClient())
        }
    }
}