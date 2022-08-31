package dev.m2en.citation

import dev.kord.common.entity.Snowflake
import dev.kord.core.event.message.ReactionAddEvent
import dev.kord.core.kordLogger

suspend fun ReactionAddEvent.onQuoteDelete(selfId: Snowflake) {
    val message = message.fetchMessage()

    if(user.id == selfId) {
        return
    }

    if(message.author?.id != selfId && emoji.name != ":wastebasket:") {
        return
    }

    // メッセージ送信者自身のメッセージを本人が削除してもDiscordの仕様上監査ログには記録されないため、理由は指定しない
    message.delete()
    kordLogger.info("削除リクエストにより、citationのメッセージを削除しました。")
}
