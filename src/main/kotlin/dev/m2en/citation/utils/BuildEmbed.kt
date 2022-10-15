package dev.m2en.citation.utils

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.Message.Attachment
import net.dv8tion.jda.api.entities.MessageEmbed

class BuildEmbed {

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

            if(message.isEdited) {
                embed.setTimestamp(message.timeEdited)
            }

            if(message.isPinned) {
                embed.addField("ピン留め", "このメッセージはピン留めされています。", true)
            }

            if(message.attachments.size == 1) {
                setAttachment(message.attachments, embed)
            }

            return embed.build()
        }

        private fun setAttachment(attachment: List<Attachment>, embed: EmbedBuilder) {
            attachment.forEach { _attachment ->
                val fileName = _attachment.fileName
                val fileUrl = _attachment.url
                // Embedの値の文字制限に引っかからないための確認処理
                // 参考: https://discord.com/developers/docs/resources/channel#embed-object-embed-limits
                if(Utils.checkLimit(fileName, 240)) {
                    embed.setImage(fileUrl)
                    return@forEach
                }

                val alt = _attachment.description
                if(alt !== null && !Utils.checkLimit(alt, 1024)) {
                    embed.addField("ALT", alt.toString(), true)
                }

                if(_attachment.isVideo) {
                    embed.addField("ビデオファイル", "[$fileName]($fileUrl)", true)
                    return@forEach
                }
                if(_attachment.isSpoiler) {
                    embed.addField("スポイラー済みの添付ファイル", "[$fileName]($fileUrl)", true)
                    return@forEach
                }

                embed.addField("添付ファイル", fileName, true)
                embed.setImage(fileUrl)
            }
        }
    }

}
