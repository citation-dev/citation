package dev.m2en.citation.message

import dev.kord.common.entity.Permission
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.MessageBehavior
import dev.kord.core.behavior.reply
import dev.kord.core.entity.ReactionEmoji
import dev.m2en.citation.handler.ReactionHandler
import dev.m2en.citation.utils.ErrorEmbed
import dev.m2en.citation.utils.Logger

class QuoteDeleteListener(private val selfId: Snowflake, private val userId: Snowflake) : ReactionHandler {

    override suspend fun canProcess(message: MessageBehavior, reaction: ReactionEmoji): Boolean =
        reaction.name == ":wastebasket:"

    override suspend fun reactionHandle(message: MessageBehavior, reaction: ReactionEmoji) {
        val targetMessage = message.fetchMessage() // 削除対象のメッセージ
        val targetMessageReference = targetMessage.messageReference ?: return

        if(selfId == userId || targetMessage.author?.id != selfId) {
            return
        }

        val targetMessageReply = targetMessageReference.message
        if(targetMessageReply == null) {
            targetMessage.reply { embeds.add(ErrorEmbed.buildErrorEmbed(
                "メッセージの削除に失敗しました。",
                "削除すべき引用のメッセージを見つけることができませんでした。",
                "Botの再起動を検討してください。"
            )) }
            return
        }

        val guild = targetMessage.getGuild()
        if(guild.getMember(userId).getPermissions().contains(Permission.ManageMessages)) {
            targetMessage.delete()
            Logger.sendInfo("メッセージ管理権限を所持しているため、削除リクエストを受理しました")
            return
        }

        if(userId != targetMessageReply.fetchMessage().author?.id) {
            Logger.sendInfo("引用者ではないため、削除リクエストを却下しました")
            return
        }

        targetMessage.delete("引用メッセージの削除")
        Logger.sendInfo("削除: 削除リクエストを受理しました")
    }
}
