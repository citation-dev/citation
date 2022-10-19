package dev.m2en.citation.internal.utils

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed

class EmbedBuilder {

    companion object {

        fun buildQuoteEmbed(message: Message, authorName: String): MessageEmbed {
            val messageAuthor = message.author
            val messageAuthorIcon = messageAuthor.avatarUrl

            val embed = EmbedBuilder().apply {
                setDescription(message.contentRaw)
                setAuthor(messageAuthor.name, null, messageAuthorIcon)
                setColor(0x7e56c2)
                setFooter(authorName)
                setTimestamp(message.timeCreated)
            }

            if (message.isEdited) {
                embed.setTimestamp(message.timeEdited)
            }

            if (message.isPinned) {
                embed.addField("ピン留め", "このメッセージはピン留めされています。", true)
            }

            if (message.attachments.size == 1) {
                FileBuilder.setAttachment(message.attachments, embed)
            }

            if(message.stickers.size == 1) {
                FileBuilder.setSticker(message.stickers, embed)
            }

            return embed.build()
        }

        /**
         * 埋め込みを作成します。
         *
         * @param name 名前
         * @param message 説明
         */
        fun buildEmbed(name: String, message: String): MessageEmbed {
            if (Utils.checkLimit(name, 256) || Utils.checkLimit(message, 4096)) {
                throw RuntimeException("Embedの作成に失敗")
            }

            return EmbedBuilder().apply {
                setTitle(name)
                setDescription(message)
                setColor(0x7e56c2)
                setAuthor("citation")
            }.build()
        }

        /**
         * エラーの埋め込みを作成します。
         *
         * @param name エラーの名前
         * @param message エラーの説明
         * @return 作成した埋め込み
         */
        fun buildErrorEmbed(name: String, message: String): MessageEmbed {
            if (Utils.checkLimit(name, 256) || Utils.checkLimit(message, 4096)) {
                throw RuntimeException("Embedの作成に失敗")
            }

            return EmbedBuilder().apply {
                setTitle(name)
                setDescription(message)
                setColor(0xeb0915)
                setAuthor("citation - エラー")
            }.build()
        }
    }

}
