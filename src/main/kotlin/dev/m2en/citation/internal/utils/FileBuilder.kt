// -------------------------------------------------------
// Created by m2en : Logic to create attachments for Embed
// -------------------------------------------------------

package dev.m2en.citation.internal.utils

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.sticker.Sticker
import net.dv8tion.jda.api.entities.sticker.StickerItem

class FileBuilder {

    companion object {

        fun setAttachment(attachment: List<Message.Attachment>, embed: EmbedBuilder) {
            attachment.forEach { _attachment ->
                val fileName = _attachment.fileName
                val fileUrl = _attachment.url

                val alt = _attachment.description
                if (alt !== null) {
                    embed.addField("ALT", alt.toString(), true)
                }

                if (_attachment.isVideo) {
                    embed.addField("ビデオファイル", "[$fileName]($fileUrl)", true)
                    return@forEach
                }
                if (_attachment.isSpoiler) {
                    embed.addField("スポイラー済みの添付ファイル", "[$fileName]($fileUrl)", true)
                    return@forEach
                }

                embed.addField("添付ファイル", "[$fileName]($fileUrl)", true)

                embed.setImage(fileUrl)
            }
        }

        fun setSticker(sticker: MutableList<StickerItem>, embed: EmbedBuilder) {
            sticker.forEach { _stickerItem ->
                val stickerName = _stickerItem.name
                val stickerType = _stickerItem.formatType
                val stickerUrl = _stickerItem.iconUrl

                embed.addField("スタンプ", stickerName, true)

                when (stickerType) {
                    Sticker.StickerFormat.PNG, Sticker.StickerFormat.APNG -> {
                        embed.setThumbnail(stickerUrl)
                    }

                    else -> return@forEach
                }
            }
        }
    }
}
