// -------------------------------------------------------
// Created by m2en : Login Logic to Discord
// -------------------------------------------------------

@file:JvmName("MainKt")

package dev.m2en.citation

import dev.m2en.citation.internal.utils.Settings
import dev.m2en.citation.internal.utils.Utils
import java.nio.file.Files
import javax.security.auth.login.LoginException
import kotlin.io.path.Path

fun main() {
    val config = loadConfig()
    try {
        val jda = Client.createClient(Utils.getEnv("CITATION_BOT_TOKEN"), getCitationVersion(), config)
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

private fun loadConfig(): Settings {
    if (!Files.exists(Path("config/config.yml"))) {
        Settings.copyConfig()
    }
    return Settings.getConfig()
}
