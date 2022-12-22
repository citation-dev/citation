package dev.m2en.citation.internal.utils

import com.charleskorn.kaml.Yaml
import kotlinx.serialization.Serializable
import java.io.File
import java.io.IOException
import java.nio.file.Files
import kotlin.io.path.Path

@Serializable
data class Settings (
    val commandRegistration: Boolean = true,
    val autoConnection: Boolean = true,
    val shutdownHook: Boolean = true
) {
    companion object {

        fun getConfig(): Settings {
            return parseConfig()
        }

        private fun parseConfig(): Settings {
           return Yaml.default.decodeFromString(serializer(), Path("config/config.yml").toFile().readText(Charsets.UTF_8))
        }

        fun copyConfig() {
            try {
                println(File(ClassLoader.getSystemResource("config.yml").toURI()).toPath())
                Files.copy(File(ClassLoader.getSystemResource("config.yml").toURI()).toPath(), Path("config/config.yml"))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
