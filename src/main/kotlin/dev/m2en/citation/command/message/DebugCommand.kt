package dev.m2en.citation.command.message

import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.event.interaction.GuildMessageCommandInteractionCreateEvent
import dev.kord.rest.builder.message.EmbedBuilder

suspend fun GuildMessageCommandInteractionCreateEvent.onDebugMessageCommand() {
    val targetMessage = interaction.channel.getMessage(interaction.target.id)

    interaction.deferEphemeralResponse().respond { embeds?.add(buildEmbed(targetMessage.content)) }
}

private fun buildEmbed(targetContent: String): EmbedBuilder {
    val debugRecruit = targetContent.replace("```", "'''")
    val embed = EmbedBuilder()

    embed.title = "メッセージデバック"
    embed.description = "```\n${debugRecruit}\n```"

    return embed
}
