package dev.group6.vrappcontroller.server

import dev.group6.vrappcontroller.stream.module
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer

object ServerInstance {
    private val server: Server
    private var isRunning = false
    val nonce: String

    init {
        val randomNonce = generateNonce()
        nonce = randomNonce
        server = Server(port = 35614, nonce = randomNonce)
        println("Server created on port 35614 with nonce: $randomNonce")
    }

    private fun generateNonce(): String {
        val chars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..4).map { chars.random() }.joinToString("")
    }

    fun start() {
        if (isRunning) return
        server.start()

        embeddedServer(
            Netty,
            port = 8086,
            module = Application::module
        ).start(wait = false)
        isRunning = true
    }

    fun stop() {
        if (!isRunning) return
        server.stop()
        isRunning = false
    }

    suspend fun broadcast(msg: Envelope) = server.broadcast(msg)
}