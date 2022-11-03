// ----------------------------------------------------
// Created by m2en : Processes delete citation requests
// ----------------------------------------------------

package dev.m2en.citation.api.event

import dev.m2en.citation.internal.utils.EmbedBuilder
import dev.m2en.citation.internal.utils.Logger
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class CitationDeleteEvent : ListenerAdapter() {

    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        val target = event.message
        val requester = event.member ?: return
        if (requester.user.isBot || requester.user.isSystem) return

        val guild = event.guild ?: return
        val channel = event.channel.asGuildMessageChannel()
        val self = guild.selfMember

        if (!checkPermission(self, channel)) {
            Logger.sendError("citation自体もしくはチャンネル[${channel.name}](${channel.id})の権限が不足しているため、メッセージの削除に失敗しました。([チャンネルの閲覧権限, メッセージの履歴閲覧権限]が必要です。)")
            event.replyEmbeds(EmbedBuilder.buildErrorEmbed("削除に失敗しました", "citation自体に権限が不足しています。サーバーの管理者に連絡してください。"))
                .setEphemeral(true).queue()
            return
        }


        if (!checkRequesterPermission(requester) && event.componentId != requester.id) {
            event.replyEmbeds(EmbedBuilder.buildErrorEmbed("削除に失敗しました", "あなたの引用ではないため、削除できません。")).setEphemeral(true).queue()
            return
        }

        try {
            event.replyEmbeds(EmbedBuilder.buildEmbed("削除に成功しました。")).setEphemeral(true).queue()
            target.delete().queue()
            Logger.sendInfo("${requester.user.name} の削除リクエストを受理しました。")
        } catch (e: IllegalStateException) {
            Logger.sendError("削除に失敗しました。", e)
            event.replyEmbeds(EmbedBuilder.buildErrorEmbed("削除に失敗しました", "利用できない不正なメッセージです。")).setEphemeral(true).queue()
        }
    }

    private fun checkPermission(self: Member, channel: GuildMessageChannel): Boolean {
        // メッセージ管理権限は必要ないため、確認しない
        if (!self.hasPermission(channel, Permission.VIEW_CHANNEL)) return false
        if (!self.hasPermission(channel, Permission.MESSAGE_HISTORY)) return false
        if (!self.hasPermission(Permission.VIEW_CHANNEL)) return false
        if (!self.hasPermission(Permission.MESSAGE_HISTORY)) return false
        return true
    }

    private fun checkRequesterPermission(requester: Member): Boolean {
        if (!requester.hasPermission(Permission.MESSAGE_MANAGE)) return false
        if (!requester.hasPermission(Permission.ADMINISTRATOR)) return false
        return true
    }
}
