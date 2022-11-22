// ------------------------------------
// Created by m2en : Ready Client Event
// ------------------------------------

package dev.m2en.citation.api.event

import dev.m2en.citation.api.manager.CommandManager
import dev.m2en.citation.internal.utils.Logger
import dev.m2en.citation.internal.utils.Utils
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class ReadyEvent(private val tag: String?) : ListenerAdapter() {

    override fun onReady(event: ReadyEvent) {
        super.onReady(event)
        Logger.sendInfo("======================")
        Logger.sendInfo("citationを起動します。")
        if (tag == null) {
            Logger.sendWarn("バージョン情報が取得できませんでした。")
        } else {
            Logger.sendInfo("起動バージョン: $tag")
        }
        Logger.sendInfo("======================")
        Logger.sendInfo("有効化ギルド数: ${event.guildTotalCount}")
        Logger.sendInfo("   (うち利用可能: ${event.guildAvailableCount})")
        Logger.sendInfo("   (うち利用不可能: ${event.guildUnavailableCount})")
        Logger.sendInfo("======================")
        Logger.sendInfo("GitHub: https://github.com/citation-dev/citation")
        Logger.sendInfo("Issue: https://github.com/citation-dev/citation/issues/new/choose")
        Logger.sendInfo("Discussion: https://github.com/citation-dev/citation/discussions")
        Logger.sendInfo("ドキュメント: https://citation.m2en.dev")
        Logger.sendInfo("======================")
        Logger.sendInfo("起動完了しました。")
        Logger.sendInfo("======================")

        val guild = event.jda.getGuildById(Utils.getEnv("GUILD_ID")) ?: throw NumberFormatException(
            "ギルドを取得することができませんでした。"
        )
        CommandManager.registerCommand(guild)
    }

}
