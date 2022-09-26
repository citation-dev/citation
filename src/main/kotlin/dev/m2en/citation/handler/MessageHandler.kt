package dev.m2en.citation.handler

import dev.kord.core.entity.Message

interface MessageHandler {

    suspend fun canProcess(message: Message): Boolean
    suspend fun messageHandle(message: Message)

}
