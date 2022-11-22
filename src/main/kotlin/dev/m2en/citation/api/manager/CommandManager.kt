// ----------------------------------
// Created by m2en : Manage Commands
// ----------------------------------

package dev.m2en.citation.api.manager

import dev.m2en.citation.internal.utils.Logger
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.interactions.DiscordLocale
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.localization.ResourceBundleLocalizationFunction

class CommandManager {

    companion object {
        fun registerCommand(guild: Guild) {
            try {

                Logger.sendInfo("ロケーション設定の読み込みを開始します....")
                val localizationFunction = ResourceBundleLocalizationFunction
                    .fromBundles("CitationCommands", DiscordLocale.ENGLISH_US)
                    .build()
                Logger.sendInfo("ロケーション設定を読み込みました。")

                Logger.sendInfo("コマンドの登録を開始します....")
                guild.updateCommands().addCommands(
                    Commands.slash("help", "ヘルプを開きます").setLocalizationFunction(localizationFunction),
                    Commands.slash("ping", "応答時間(Ping)を表示する").setLocalizationFunction(localizationFunction),
                ).queue()
                Logger.sendInfo("コマンドの登録に成功しました。")

            } catch (e: IllegalArgumentException) {
                Logger.sendError("コマンドの登録に失敗しました。")
                e.printStackTrace()
            }
        }
    }
}
