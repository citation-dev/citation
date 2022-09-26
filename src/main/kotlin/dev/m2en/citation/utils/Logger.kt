package dev.m2en.citation.utils

import dev.kord.core.kordLogger

class Logger {

    companion object {

        /**
         * メッセージを通常レベルでコンソールに送信します。
         *
         * @param message ログに出力する文字列
         */
        fun sendInfo(message: String) {
            println("[INFO] $message")
        }

        /**
         * メッセージをKordLogger経由でコンソールに送信します。
         *
         * @param message ログに出力する文字列
         */
        fun sendKInfo(message: String) {
            kordLogger.info { "[INFO] $message" }
        }

        /**
         * エラーメッセージをKordLogger経由でコンソールに送信します。
         *
         * @param message ログに出力する文字列
         * @param throwable エラーの例外
         */
        fun sendError(message: String, throwable: Throwable? = null) {
            if(throwable == null) {
                kordLogger.warn { "[WARN] $message" }
            } else {
                kordLogger.warn { "[WARN] $message \n $throwable" }
            }
        }

        /**
         * 警告メッセージをコンソールに送信します。
         *
         * @param message ログに出力する文字列
         */
        fun sendWarn(message: String) {
            println("[WARN] $message")
        }

        /**
         * 警告メッセージをKordLogger経由でコンソールに送信します。
         *
         * @param message ログに出力する文字列
         */
        fun sendKWarn(message: String) {
            kordLogger.warn { "[WARN] $message" }
        }

        /**
         * 起動メッセージをコンソールに送信します。
         *
         * @param versionTag citationのバージョンタグ
         */
        fun sendReadyInfo(versionTag: String?) {
            sendInfo("citationを起動しました。")

            if(versionTag == null || versionTag == "null") {
                sendWarn("バージョン情報が取得できませんでした。")
            } else {
                sendInfo("バージョン: v$versionTag")
            }
        }

    }
}
