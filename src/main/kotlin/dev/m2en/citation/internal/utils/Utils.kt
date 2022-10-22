package dev.m2en.citation.internal.utils

import io.github.cdimascio.dotenv.dotenv
import java.awt.Color

class Utils {

    companion object {

        private val dotenv = dotenv()

        /**
         * 環境変数を取得する
         *
         * @param key 環境変数のKey
         * @return 環境変数の値(存在しない場合は例外)
         */
        fun getEnv(key: String): String {
            return dotenv.get(key) ?: throw Error("次の環境変数が指定されていません: $key")
        }
    }
}
