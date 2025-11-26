package dev.group6.vrappcontroller.stream

import io.ktor.server.application.*
import io.ktor.server.http.content.*

import io.ktor.server.routing.*
import io.ktor.server.websocket.*

import io.ktor.websocket.*

import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonPrimitive


fun Application.module() {
    install(WebSockets)
    reset()

    routing {
        route("/") {
            webSocket("") {
                add(this)
                for (frame in incoming) {
                    if (frame is Frame.Text) {

                        val text = frame.readText()

                        val msg = Json.decodeFromString<Map<String, JsonElement>>(text)

                        val type = msg["type"]?.jsonPrimitive?.content ?: continue
                        val ws = this
                        when (type) {
                            "connect" -> {
                                val connectionId = msg["connectionId"]?.jsonPrimitive?.content ?: continue
                                launch { onConnect(ws, connectionId) }
                            }

                            "disconnect" -> {
                                val connectionId = msg["connectionId"]?.jsonPrimitive?.content ?: continue
                                launch { onDisconnect(ws, connectionId) }
                            }

                            "offer" -> {
                                val connectionId = msg["from"]?.jsonPrimitive?.content ?: continue
                                val sdp = (msg["data"] as JsonObject)["sdp"]?.jsonPrimitive?.content ?: continue

                                launch { onOffer(ws, connectionId, sdp) }
                            }

                            "answer" -> {
                                val connectionId = msg["from"]?.jsonPrimitive?.content ?: continue
                                val sdp = (msg["data"] as JsonObject)["sdp"]?.jsonPrimitive?.content ?: continue
                                launch { onAnswer(ws, connectionId, sdp) }
                            }

                            "candidate" -> {
                                val connectionId = msg["from"]?.jsonPrimitive?.content ?: continue
                                val data = (msg["data"] as JsonObject)
                                val candidate = data["candidate"]?.jsonPrimitive?.content ?: continue
                                val sdpMLineIndex = data["sdpMLineIndex"]?.jsonPrimitive?.intOrNull ?: continue
                                val sdpMid = data["sdpMid"]?.jsonPrimitive?.content ?: continue

                                launch { onCandidate(ws, connectionId, candidate, sdpMLineIndex, sdpMid) }
                            }

                            else -> {}
                        }
                    }
                }
            }
        }

        staticResources("/", "files/web") {}
    }
}