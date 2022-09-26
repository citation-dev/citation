package dev.m2en.citation.handler

import dev.kord.core.behavior.MessageBehavior
import dev.kord.core.entity.ReactionEmoji

interface ReactionHandler {

    suspend fun canProcess(message: MessageBehavior, reaction: ReactionEmoji): Boolean
    suspend fun reactionHandle(message: MessageBehavior, reaction: ReactionEmoji)
}
