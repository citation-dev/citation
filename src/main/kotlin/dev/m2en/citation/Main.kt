@file:JvmName("MainKt")

package dev.m2en.citation

import dev.m2en.citation.internal.utils.Utils
import javax.security.auth.login.LoginException

fun main() {
    try {
        val jda = Client.createClient(Utils.getEnv("CITATION_BOT_TOKEN"), getCitationVersion())
        jda.build()
    } catch (e: LoginException) {
        e.printStackTrace()
    }
}

/**
 * citationのバージョンとコミットハッシュをbuild.gradle.ktsから取得する
 *
 * @return バージョンとコミットハッシュ(implementationVersion)
 */
private fun getCitationVersion(): String {
    val clazz = object {}.javaClass
    val tag = clazz.`package`.implementationVersion ?: return "バージョン取得に失敗"
    return "v$tag"
}
