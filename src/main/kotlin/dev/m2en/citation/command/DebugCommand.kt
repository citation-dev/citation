package dev.m2en.citation.command

import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.interaction.response.DeferredEphemeralMessageInteractionResponseBehavior
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.entity.interaction.GuildChatInputCommandInteraction
import dev.kord.core.kordLogger
import dev.kord.rest.builder.message.EmbedBuilder

object DebugCommand: InteractionCommandInterface {

    override suspend fun onCommand(interaction: GuildChatInputCommandInteraction, responseBehavior: DeferredEphemeralMessageInteractionResponseBehavior) {
        try {
            val command = interaction.command
            val recruit = interaction.channel.getMessage(Snowflake(command.strings["target"]!!))

            responseBehavior.respond { embeds = MutableList(1) { buildEmbed(recruit.content) } }
            kordLogger.info("デバック: ${interaction.user.tag} が メッセージID:${command.strings["target-message"]} をデバックしました")
        } catch (e: Exception) {
            responseBehavior.respond { content = "> **エラー:** デバックに失敗しました" }
            kordLogger.error("デバック: ${interaction.user.tag} がリクエストしたメッセージデバックに失敗しました (Reason: ${e.message})")
        }
    }

    private fun buildEmbed(recruit: String): EmbedBuilder {
        val embed = EmbedBuilder()
        embed.title = "メッセージデバック"
        val debugRecruit = recruit.replace("```", "'''")
        embed.description = "```\n${debugRecruit}\n```"
        return embed
    }


}
