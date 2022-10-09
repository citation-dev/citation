package dev.m2en.citation.command.chat

import dev.kord.core.behavior.interaction.respondEphemeral
import dev.kord.core.behavior.interaction.response.DeferredEphemeralMessageInteractionResponseBehavior
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.entity.Guild
import dev.kord.core.entity.interaction.GuildChatInputCommandInteraction
import dev.m2en.citation.handler.InteractionCommandInterface
import dev.m2en.citation.utils.ErrorEmbed

object PurgeCommand: InteractionCommandInterface {

    override suspend fun onCommand(
        interaction: GuildChatInputCommandInteraction,
        responseBehavior: DeferredEphemeralMessageInteractionResponseBehavior,
    ) {
        val command = interaction.command
        val target = command.users["target"]?.fetchMember(interaction.guildId) ?: return
        val count = command.integers["count"] ?: return

        if(count < 1 || count > 100) {
            responseBehavior.respond {
                embeds = MutableList(1) { ErrorEmbed.buildErrorEmbed(
                    "範囲エラー",
                    "削除するメッセージ数は1~100の範囲で指定してください"
                ) }
            }
            return
        }

    }
}
