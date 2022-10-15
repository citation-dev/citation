@file:JvmName("MainKt")

package dev.m2en.citation

import dev.m2en.citation.event.ReadyEvent
import dev.m2en.citation.utils.Utils

fun main() {
    val jda = Client.createClient(Utils.getEnv("CITATION_BOT_TOKEN"), getCitationVersion())

    jda.addEventListeners(ReadyEvent(getCitationVersion()))

    jda.build()
}

/**
 * citationのバージョンとコミットハッシュをbuild.gradle.ktsから取得する
 *
 * @return バージョンとコミットハッシュ(implementationVersion)
 */
private fun getCitationVersion(): String {
    val clazz = object{}.javaClass
    val tag = clazz.`package`.implementationVersion ?: return "バージョン取得に失敗"
    return "v$tag"
}
