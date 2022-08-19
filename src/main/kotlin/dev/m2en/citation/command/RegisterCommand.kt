package dev.m2en.citation.command

import dev.kord.common.entity.Permission
import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message
import dev.kord.core.kordLogger
import io.github.cdimascio.dotenv.dotenv

object RegisterCommand: MessageCommandInterface {

    private val dotenv = dotenv()

    override suspend fun onCommand(message: Message) {
        val member = message.author?.id?.let { message.getGuild().getMemberOrNull(it) } ?: return

        val memberPermission = member.getPermissions()
        if(memberPermission.contains(Permission.ManageGuild) || memberPermission.contains(Permission.Administrator)) {
            message.reply { content = "> **エラー:** 権限が足りません。このコマンドを実行するには **サーバーの管理権限(`ManageGuild`)** が必要です。" }
            return
        }

        val kord = message.kord
        try {
            kordLogger.info("Application Commandの登録を開始します....")
            message.reply { content = "Application Commandの登録を開始します...." }

            registerApplicationCommand(kord)

            kordLogger.info("Application Commandの登録に成功しました")
            message.reply { content = "Application Commandの登録に成功しました\n(コマンドが反映していない場合はDiscordクライアントの再起動をしてください)" }
        } catch (e: Exception) {
            kordLogger.error("Application Commandの登録に失敗しました。", e)
            message.reply { content = "Application Commandの登録に失敗しました。詳細はログを参照してください。" }
        }
    }

    private suspend fun registerApplicationCommand(kord: Kord) {
        // /help
        kord.createGuildChatInputCommand(
            Snowflake(dotenv.get("GUILD_ID")),
            "help",
            "ヘルプを表示します"
        )

        kord.createGuildMessageCommand(
            Snowflake(dotenv.get("GUILD_ID")),
            "Debug"
        )
    }
}
