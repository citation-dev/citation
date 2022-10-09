package dev.m2en.citation.command.message.chat

import dev.m2en.citation.utils.Permission
import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.behavior.edit
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message
import dev.kord.core.entity.ReactionEmoji
import dev.m2en.citation.handler.MessageHandler
import dev.m2en.citation.utils.Logger

class RegisterCommand(private val guildId: Snowflake) : MessageHandler {
    override suspend fun canProcess(message: Message): Boolean = message.author?.isBot == false

    override suspend fun messageHandle(message: Message) {
        // <メンション> register
        if (message.content !== "<@${message.kord.selfId}> register") {
            return
        }

        val member = message.author?.id?.let { message.getGuild().getMemberOrNull(it) } ?: return

        if (Permission.isDangerPermission(member.getPermissions())) {
            message.reply {
                content =
                    "> **エラー:** 権限が足りません。このコマンドを実行するには **サーバーの管理権限(`ManageGuild`) または 管理者権限(`Administrator`)** が必要です。"
            }
            return
        }

        val statusMessage = message.reply { content = "Application Commandの登録を開始します.... (実行結果はコンソールを確認してください)" }
        statusMessage.addReaction(ReactionEmoji.Unicode("\uD83D\uDDD1"))

        registerApplicationCommand(message.kord, guildId)

        statusMessage.edit { content = "Application Commandの登録が完了しました。" }

    }

    suspend fun registerApplicationCommand(kord: Kord, guildId: Snowflake) {

        try {
            Logger.sendInfo("Application Commandの登録を開始します....")

            targetApplicationCommand(kord, guildId)

            Logger.sendInfo("Application Commandの登録に成功しました")
        } catch (e: Exception) {
            Logger.sendError("Application Commandの登録に失敗しました。", e)
        }
    }

    private suspend fun targetApplicationCommand(kord: Kord, guildId: Snowflake) {
        kord.createGuildChatInputCommand(
            guildId,
            "help",
            "ヘルプを表示します"
        )

        kord.createGuildMessageCommand(
            guildId,
            "Debug"
        )
    }
}
