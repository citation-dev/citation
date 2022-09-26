@file:JvmName("MainKt")

package dev.m2en.citation

import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.entity.ReactionEmoji
import dev.kord.core.event.gateway.ReadyEvent
import dev.kord.core.event.interaction.GuildChatInputCommandInteractionCreateEvent
import dev.kord.core.event.interaction.GuildMessageCommandInteractionCreateEvent
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.kordLogger
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.Intents
import dev.kord.core.event.message.ReactionAddEvent
import dev.kord.gateway.PrivilegedIntent
import dev.m2en.citation.command.*
import dev.m2en.citation.command.chat.HelpCommand
import dev.m2en.citation.command.message.onDebugMessageCommand
import dev.m2en.citation.command.message.onRegister
import dev.m2en.citation.message.quote.QuoteSendListener
import dev.m2en.citation.message.quote.onQuoteDelete
import io.github.cdimascio.dotenv.dotenv

@OptIn(PrivilegedIntent::class)
suspend fun main() {
    val dotenv = dotenv()
    val kord = Kord(dotenv.get("CITATION_BOT_TOKEN"))

    val interactionMap = mutableMapOf<String, InteractionCommandInterface>()
    interactionMap["help"] = HelpCommand

    val reactionEmoji = ReactionEmoji.Unicode("\uD83D\uDDD1")

    kord.on<ReadyEvent> {
        println("citation is ready!\n実行中バージョン: v" + getCitationVersion())
    }

    kord.on<MessageCreateEvent> {
        if(message.getGuildOrNull() == null) return@on

        onRegister(reactionEmoji)
        QuoteSendListener.messageHandle(message)
    }

    kord.on<ReactionAddEvent> {
        onQuoteDelete(kord.selfId)
    }

    kord.on<GuildChatInputCommandInteractionCreateEvent> {
        val responseBehavior = interaction.deferEphemeralResponse()

        if(interaction.getGuildOrNull() == null) {
            responseBehavior.respond { content = "> **エラー:** ギルド以外から発行されたインタラクションには応答できません" }
            return@on
        }

        // インタラクションが発行されたコマンドを取得、本来用意されていないコマンドのインタラクションが発行された場合はエラーをLoggerから報告する
        when(interaction.invokedCommandName) {
            "help" -> interactionMap["help"]?.onCommand(interaction, responseBehavior)
            "debug" -> interactionMap["debug"]?.onCommand(interaction, responseBehavior)

            else -> {
                responseBehavior.respond { content = "> **エラー:** このインタラクションはcitationで本来想定されていません" }
                kordLogger.warn("警告: citationで本来想定されていない不正なインタラクションを受信しました。レスポンスは返さず無視します")
                return@on
            }
        }

        // 発行されたインタラクションについて報告する
        kordLogger.info("ログ: ${interaction.user.tag} が ${interaction.invokedCommandName}(${interaction.invokedCommandId}) を実行しました")
    }

    kord.on<GuildMessageCommandInteractionCreateEvent> {
        when(interaction.invokedCommandName) {
            "Debug" -> onDebugMessageCommand()
        }
    }

    kord.login {
        intents = Intents(Intent.Guilds, Intent.GuildMessages, Intent.MessageContent, Intent.GuildMessageReactions, Intent.GuildEmojis)
    }
}

/**
 * citationのバージョンとコミットハッシュをbuild.gradle.ktsから取得する
 *
 * @return バージョンとコミットハッシュ(implementationVersion)
 */
fun getCitationVersion(): String? {
    val clazz = object{}.javaClass
    return clazz.`package`.implementationVersion
}
