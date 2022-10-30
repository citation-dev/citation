// --------------------------------------------------------
// Created by m2en : Logic to retrieve and inspect messages
// --------------------------------------------------------

package dev.m2en.citation.internal.manager

import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageType
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel

class MessageManager {

    companion object {

        /**
         * メッセージを取得する
         *
         * @param channel 検索対象のチャンネル
         * @param id 検索に使用するID
         */
        fun getMessage(channel: GuildMessageChannel, id: String): Message {
            val message = channel.retrieveMessageById(id).complete()
                ?: throw IllegalArgumentException("メッセージを取得することができませんでした")

            if (!checkMessageType(message)) {
                throw RuntimeException("メッセージタイプが不正です")
            }

            return message
        }

        /**
         * メッセージタイプが利用可能なのかを確認する
         *
         * @param message メッセージ
         * @return true -> 利用可能, false -> 利用できない
         */
        private fun checkMessageType(message: Message): Boolean {
            if (message.embeds.isNotEmpty() && message.contentRaw.isEmpty()) return false
            return when (message.type) {
                // 通常メッセージ、返信、スラッシュコマンド、コンテキストメニュー、スレッドの初期メッセージのみを受け入れる
                MessageType.DEFAULT, MessageType.INLINE_REPLY,
                MessageType.SLASH_COMMAND, MessageType.THREAD_STARTER_MESSAGE,
                MessageType.CONTEXT_COMMAND -> true

                else -> false
            }
        }

    }
}
