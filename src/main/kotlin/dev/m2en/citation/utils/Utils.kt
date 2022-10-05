package dev.m2en.citation.utils

import dev.kord.common.entity.Snowflake

class Utils {

    companion object {
        /**
         * citation準拠のユーザー表示名を作成します。
         * @param userName ユーザー名, メンションタイプを作成する際は使用されません。(デフォルト値: 空文字列)
         * @param userId ユーザーID
         * @param mentionType メンションタイプとして表示するかどうか (Embedで表示する際はこれを有効にしてください, デフォルト値: false)
         */
        fun createUserDisplayName(userName: String = "", userId: Snowflake, mentionType: Boolean = false): String {
            return if(mentionType) {
                "<@${userId}>"
            } else {
                "${userName}(${userId})"
            }
        }
    }
}
