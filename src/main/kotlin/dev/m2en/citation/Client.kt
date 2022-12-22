// ------------------------------------------
// Made by m2en : Build citation client logic
// ------------------------------------------

package dev.m2en.citation

import dev.m2en.citation.api.command.HelpCommand
import dev.m2en.citation.api.command.PingCommand
import dev.m2en.citation.api.event.ReadyEvent
import dev.m2en.citation.api.event.CitationCreateEvent
import dev.m2en.citation.api.event.CitationDeleteEvent
import dev.m2en.citation.internal.utils.Settings
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
        fun createClient(token: String, tag: String, config: Settings): JDABuilder {
            return JDABuilder
                .createDefault(token)
                .setActivity(Activity.playing("/help | $tag"))
                .setBulkDeleteSplittingEnabled(true)
                .setAutoReconnect(config.autoConnection)
                .setEnableShutdownHook(config.shutdownHook)

                // メッセージコンテンツ、ギルドメンバー、ギルドメッセージのインテントを有効化
                .setEnabledIntents(
                    GatewayIntent.MESSAGE_CONTENT,
                    GatewayIntent.GUILD_MEMBERS,
                    GatewayIntent.GUILD_MESSAGES
                )

                // citationでは使用しないインテントを無効化
                .setDisabledIntents(
                    GatewayIntent.GUILD_VOICE_STATES,
                    GatewayIntent.GUILD_PRESENCES,
                    GatewayIntent.GUILD_BANS,
                    GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                    GatewayIntent.GUILD_WEBHOOKS,
                    GatewayIntent.GUILD_INVITES,
                    GatewayIntent.GUILD_MESSAGE_REACTIONS,
                    GatewayIntent.GUILD_MESSAGE_TYPING,
                    GatewayIntent.DIRECT_MESSAGES,
                    GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                    GatewayIntent.DIRECT_MESSAGE_TYPING,
                    GatewayIntent.SCHEDULED_EVENTS
                )

                // 不要なキャッシュは貯めないように無効化
                .disableCache(
                    CacheFlag.VOICE_STATE,
                    CacheFlag.CLIENT_STATUS,
                    CacheFlag.ACTIVITY,
                    CacheFlag.EMOJI,
                    CacheFlag.STICKER,
                    CacheFlag.MEMBER_OVERRIDES,
                    CacheFlag.ROLE_TAGS,
                    CacheFlag.FORUM_TAGS,
                    CacheFlag.ONLINE_STATUS,
                    CacheFlag.SCHEDULED_EVENTS
                )
                .addEventListeners(
                    // Event:
                    ReadyEvent(tag, config),
                    CitationCreateEvent(),
                    // Command:
                    HelpCommand(tag),
                    PingCommand(),
                    // Button:
                    CitationDeleteEvent()
                )

        }
    }
}
