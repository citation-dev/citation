package dev.m2en.citation.command.message.chat

import dev.kord.common.entity.Permission
import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.behavior.edit
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message
import dev.kord.core.entity.ReactionEmoji
import dev.kord.core.kordLogger
import dev.m2en.citation.handler.MessageHandler
import io.github.cdimascio.dotenv.dotenv

object RegisterCommand: MessageHandler {
    override suspend fun canProcess(message: Message): Boolean = message.author?.isBot == false && message.content == "!register"

    override suspend fun messageHandle(message: Message) {
        val dotenv = dotenv()
        val member = message.author?.id?.let { message.getGuild().getMemberOrNull(it) } ?: return

        val memberPermission = member.getPermissions()
        if(!(memberPermission.contains(Permission.ManageGuild)) || !(memberPermission.contains(Permission.Administrator))) {
            message.reply { content = "> **エラー:** 権限が足りません。このコマンドを実行するには **サーバーの管理権限(`ManageGuild`) または 管理者権限(`Administrator`)** が必要です。" }
            return
        }

        val statusMessage = message.reply { content = "Application Commandの登録を開始します...." }
        statusMessage.addReaction(ReactionEmoji.Unicode("\uD83D\uDDD1"))

        val kord = message.kord
        try {
            kordLogger.info("Application Commandの登録を開始します....")

            registerApplicationCommand(kord, dotenv.get("GUILD_ID"))

            kordLogger.info("Application Commandの登録に成功しました")
            statusMessage.edit { content = "Application Commandの登録に成功しました\n(コマンドが反映していない場合はDiscordクライアントの再起動をしてください)" }
        } catch (e: Exception) {
            kordLogger.error("Application Commandの登録に失敗しました。", e)
            statusMessage.edit { content = "Application Commandの登録に失敗しました。詳細はログを参照してください。\n登録方法が正しいかcitation docsを参照することもお勧めします。\n<https://citation.m2en.dev/getting-started/>" }
        }
    }

    private suspend fun registerApplicationCommand(kord: Kord, guildId: String) {
        kord.createGuildChatInputCommand(
            Snowflake(guildId),
            "help",
            "ヘルプを表示します"
        )

        kord.createGuildMessageCommand(
            Snowflake(guildId),
            "Debug"
        )
    }
}
