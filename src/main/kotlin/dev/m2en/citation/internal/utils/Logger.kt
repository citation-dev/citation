// --------------------------------
// Created by m2en : Logging System
// --------------------------------

package dev.m2en.citation.internal.utils

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
         * エラーメッセージをKordLogger経由でコンソールに送信します。
         *
         * @param message ログに出力する文字列
         * @param throwable エラーの例外
         */
        fun sendError(message: String, throwable: Throwable? = null) {
            if (throwable == null) {
                println { "[ERROR] $message" }
            } else {
                println { "[ERROR] $message \n $throwable" }
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

    }
}
