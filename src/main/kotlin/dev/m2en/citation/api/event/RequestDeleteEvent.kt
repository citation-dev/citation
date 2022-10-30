// ----------------------------------------------------
// Created by m2en : Processes delete citation requests
// ----------------------------------------------------

package dev.m2en.citation.api.event

import dev.m2en.citation.internal.utils.EmbedBuilder
import dev.m2en.citation.internal.utils.Logger
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.buttons.ButtonInteraction

class RequestDeleteEvent : ListenerAdapter() {

    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        val target = event.message
        val requester = event.member ?: return

        if (requester.user.isBot || requester.user.isSystem) return
        if (!requester.hasPermission(
                Permission.MESSAGE_MANAGE,
                Permission.ADMINISTRATOR
            ) && event.componentId != requester.id
        ) {
            Logger.sendWarn("${requester.user.name} の削除リクエストを受理しました。")
            sendResult(event, "権限が不足しています。", true)
            return
        }

        sendResult(event, null, false)
        target.delete().queue()
        Logger.sendInfo("${requester.user.name} の削除リクエストを受理しました。")
    }

    private fun sendResult(
        interaction: ButtonInteraction,
        message: String?,
        error: Boolean = false
    ) {
        if (error) {
            interaction.replyEmbeds(EmbedBuilder.buildErrorEmbed("削除に失敗しました。", message))
                .setEphemeral(true).queue()
        } else {
            interaction.replyEmbeds(EmbedBuilder.buildEmbed("削除に成功しました。", message))
                .setEphemeral(true).queue()
        }
    }
}
