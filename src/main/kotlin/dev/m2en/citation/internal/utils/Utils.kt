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

        /**
         * HSBカラーコードをランダムに返します。
         *
         * @return ランダムに選択されたHSBカラー
         */
        @Deprecated("v2.0.0から導入されましたが不具合により、現在は使用されていません。今後不具合が修正されない場合は削除されます。")
        fun randomColor(): Color {
            val h = (0..0.999.toInt()).random().toFloat()
            val s = (0..1).random().toFloat()
            val b = (0..1).random().toFloat()

            return Color.getHSBColor(h, s, b)
        }

        /**
         * 埋め込み上で使えるマークダウンリンクを作成します。
         *
         * @param url URL
         * @param name マークダウンリンクの名前
         * @return マークダウンリンク
         */
        fun markdownLink(url: String, name: String): String {
            return "[$name]($url)"
        }
    }
}
