package dev.m2en.citation.event

import dev.m2en.citation.utils.Logger
import dev.m2en.citation.utils.Utils
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.Command
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands

class ReadyEvent(private val tag: String?): ListenerAdapter() {

    override fun onReady(event: ReadyEvent) {
        super.onReady(event)
        Logger.sendInfo("======================")
        Logger.sendInfo("citationを起動します。")
        if(tag == null) {
            Logger.sendWarn("バージョン情報が取得できませんでした。")
        } else {
            Logger.sendInfo("起動バージョン: $tag")
        }
        Logger.sendInfo("======================")
        Logger.sendInfo("有効化ギルド数: ${event.guildTotalCount}")
        Logger.sendInfo("   (うち利用可能: ${event.guildAvailableCount})")
        Logger.sendInfo("   (うち利用不可能: ${event.guildUnavailableCount})")
        Logger.sendInfo("======================")
        Logger.sendInfo("GitHub: https://github.com/m2en/citation")
        Logger.sendInfo("Issue: https://github.com/m2en/citation/issues/new/choose")
        Logger.sendInfo("Discussion: https://github.com/m2en/citation/discussions")
        Logger.sendInfo("ドキュメント: https://citation.m2en.dev")
        Logger.sendInfo("======================")
        Logger.sendInfo("起動完了しました。")
        Logger.sendInfo("======================")

        val guild = event.jda.getGuildById(Utils.getEnv("GUILD_ID")) ?: throw NumberFormatException("ギルドを取得することができませんでした。")
        registerCommand(guild)
    }

}

private fun registerCommand(guild: Guild) {
    try {
        guild.updateCommands().addCommands(
            Commands.slash("help", "ヘルプを開く"),
            Commands.slash("docs", "ドキュメントを開く")
                .addOption(OptionType.STRING, "query", "指定したページまでのリンクを表示します", true, true),
            Commands.slash("github", "GitHubを開く")
                .addOption(OptionType.STRING, "query", "指定したメニューまでのリンクを表示します", true, true),
            Commands.slash("shutdown", "アクティブなcitationのプロセスを終了します。")
                .addOption(OptionType.BOOLEAN, "force", "強制終了を行いますか?", true)
        ).queue()
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    }
}
