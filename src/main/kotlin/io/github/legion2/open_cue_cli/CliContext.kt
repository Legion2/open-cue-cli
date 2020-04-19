package io.github.legion2.open_cue_cli

import io.github.legion2.open_cue_cli.client.CueSdkHttpServer

class CliContext(
        val sdkClient: CueSdkHttpServer) {
    companion object {
        fun createCliContext(host: String, port: Int): CliContext {
            return CliContext(CueSdkHttpServer(host, port))
        }
    }
}