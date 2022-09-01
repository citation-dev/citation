package dev.m2en.citation

import dev.kord.common.entity.Snowflake
import dev.kord.core.event.message.ReactionAddEvent
import dev.kord.core.kordLogger

suspend fun ReactionAddEvent.onQuoteDelete(selfId: Snowflake) {
    val targetMessage = message.fetchMessage() // 削除対象のメッセージ
    val targetMessageReference = targetMessage.messageReference ?: return

    if(user.id == selfId) {
        return
    }

    if(targetMessage.author?.id != selfId && emoji.name != ":wastebasket:") {
        return
    }

    val targetMessageReply = targetMessageReference.message?.id?.let { channel.getMessageOrNull(it) } ?: return
    if(user.id != targetMessageReply.author?.id) {
        return
    }

    // メッセージ送信者自身のメッセージを本人が削除してもDiscordの仕様上監査ログには記録されないため、理由は指定しない
    targetMessage.delete()
    kordLogger.info("削除リクエストにより、citationのメッセージを削除しました。")
}
