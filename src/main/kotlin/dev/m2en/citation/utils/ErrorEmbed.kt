package dev.m2en.citation.utils

import dev.kord.rest.builder.message.EmbedBuilder

class ErrorEmbed {
    companion object {
        /**
         * エラーの際に送信する埋め込みを作成します。
         *
         * @param errorName エラー名
         * @param reason エラーの理由
         * @param solution エラーの解決方法
         * @return 作成された埋め込み
         */
        fun buildErrorEmbed(errorName: String, reason: String, solution: String = "なし"): EmbedBuilder {
            val embed = EmbedBuilder().apply {
                title = "エラーが発生しました。"
                description = "エラー名: $errorName"
                field {
                    name = "理由"
                    value = reason
                }
                field {
                    name = "解決方法"
                    value = solution
                }
            }
            return embed
        }
    }
}
