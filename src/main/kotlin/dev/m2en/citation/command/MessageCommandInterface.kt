package dev.m2en.citation.command

import dev.kord.core.entity.Message

interface MessageCommandInterface {
    suspend fun onCommand(message: Message)
}
