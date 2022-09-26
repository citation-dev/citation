package dev.m2en.citation.message

import dev.kord.common.entity.Permission
import dev.kord.common.entity.Snowflake
import dev.kord.core.event.message.ReactionAddEvent
import dev.kord.core.kordLogger

suspend fun ReactionAddEvent.onQuoteDelete(selfId: Snowflake) {
    val targetMessage = message.fetchMessage() // 削除対象のメッセージ
    val targetMessageReference = targetMessage.messageReference ?: return

    if(userId == selfId) {
        return
    }

    if(targetMessage.author?.id != selfId && emoji.name != ":wastebasket:") {
        return
    }

    val targetMessageReply = targetMessageReference.message?.id?.let { channel.getMessageOrNull(it) } ?: return
    if(userId != targetMessageReply.author?.id) {
        if(guild?.getMember(userId)?.getPermissions()?.contains(Permission.ManageMessages) == true) {
            targetMessage.delete()
            kordLogger.info("削除: メッセージ管理権限を所持しているため、${user.asUser().tag} の削除リクエストを受理しました")
        }
        kordLogger.warn("警告: ${user.asUser().tag} は引用者(${targetMessageReply.author?.tag})ではないため、削除リクエストを却下しました")
        return
    }

    // メッセージ送信者自身のメッセージを本人が削除してもDiscordの仕様上監査ログには記録されないため、理由は指定しない
    targetMessage.delete()
    kordLogger.info("削除: ${user.asUser().tag} の削除リクエストを受理しました")
}
