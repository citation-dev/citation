package dev.m2en.citation.internal.utils

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed

class EmbedBuilder {

    companion object {

        /**
         * 埋め込みを作成します。
         *
         * @param name 名前
         * @param message 説明
         */
        fun buildEmbed(name: String, message: String? = null): MessageEmbed {
            return EmbedBuilder().apply {
                setTitle(name)
                setDescription(message ?: "")
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
        fun buildErrorEmbed(name: String, message: String? = null): MessageEmbed {
            return EmbedBuilder().apply {
                setTitle(name)
                setDescription(message ?: "")
                setColor(0xeb0915)
                setAuthor("citation - エラー")
            }.build()
        }
    }

}
