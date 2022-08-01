package dev.m2en.citation

import dev.kord.core.Kord
import dev.kord.core.event.gateway.ReadyEvent
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.Intents
import dev.kord.gateway.PrivilegedIntent
import dev.m2en.citation.command.InteractionCommand
import io.github.cdimascio.dotenv.dotenv

@OptIn(PrivilegedIntent::class)
suspend fun main() {
    val dotenv = dotenv()
    val kord = Kord(dotenv.get("CITATION_BOT_TOKEN"))

    kord.on<ReadyEvent> {
        println("citation is ready!")
    }

    kord.on<MessageCreateEvent> {
        if(message.author?.isBot == true || message.getGuildOrNull() == null) return@on
        InteractionCommand.
    }

    kord.login() {
        intents = Intents(Intent.Guilds, Intent.GuildMessages, Intent.MessageContent)
    }
}

