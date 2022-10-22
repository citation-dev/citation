package dev.m2en.citation

import dev.m2en.citation.api.command.HelpCommand
import dev.m2en.citation.api.command.QueryCommand
import dev.m2en.citation.api.event.AutoCompleteCreateEvent
import dev.m2en.citation.api.event.ReadyEvent
import dev.m2en.citation.api.event.RequestCreateEvent
import dev.m2en.citation.api.event.RequestDeleteEvent
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.cache.CacheFlag

class Client {

    companion object {
        /**
         * 初期設定を行ったクライアントを返します。
         * メインスレッド上で .build() する必要があります。
         *
         * @param token 接続を行うBotのトークン
         * @param tag citationのバージョン
         * @return クライアント
         */
        fun createClient(token: String, tag: String): JDABuilder {
            return JDABuilder
                .createDefault(token)
                .setActivity(Activity.playing("/help | $tag"))
                .setBulkDeleteSplittingEnabled(true)
                .setAutoReconnect(true)
                .disableCache(
                    CacheFlag.VOICE_STATE,
                    CacheFlag.ROLE_TAGS,
                )
                .setDisabledIntents(
                    GatewayIntent.GUILD_BANS,
                    GatewayIntent.GUILD_WEBHOOKS,
                    GatewayIntent.GUILD_MESSAGE_TYPING,
                )
                .setEnabledIntents(
                    GatewayIntent.GUILD_MEMBERS,
                    GatewayIntent.GUILD_MESSAGES,
                    GatewayIntent.GUILD_MESSAGE_REACTIONS,
                    GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                    GatewayIntent.MESSAGE_CONTENT,
                )
                .addEventListeners(
                    // Event:
                    ReadyEvent(tag),
                    RequestCreateEvent(),
                    // Command:
                    AutoCompleteCreateEvent(),
                    HelpCommand(tag),
                    QueryCommand,
                    // Button:
                    RequestDeleteEvent()
                )

        }
    }
}
