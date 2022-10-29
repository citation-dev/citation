package dev.m2en.citation

import dev.m2en.citation.api.command.HelpCommand
import dev.m2en.citation.api.command.PingCommand
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
                /**
                 * .disableCache(
                CacheFlag.VOICE_STATE,
                CacheFlag.ROLE_TAGS,
                CacheFlag.SCHEDULED_EVENTS
                )
                .setDisabledIntents(
                GatewayIntent.GUILD_BANS,
                GatewayIntent.GUILD_WEBHOOKS,
                GatewayIntent.GUILD_MESSAGE_TYPING,
                GatewayIntent.SCHEDULED_EVENTS
                )
                .setEnabledIntents(
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_MESSAGE_REACTIONS,
                GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                GatewayIntent.MESSAGE_CONTENT,
                )
                 */
                .setEnabledIntents(
                    GatewayIntent.MESSAGE_CONTENT,
                    GatewayIntent.GUILD_MEMBERS,
                    GatewayIntent.GUILD_MESSAGES
                )
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
                    ReadyEvent(tag),
                    RequestCreateEvent(),
                    // Command:
                    AutoCompleteCreateEvent(),
                    HelpCommand(tag),
                    PingCommand(),
                    QueryCommand,
                    // Button:
                    RequestDeleteEvent()
                )

        }
    }
}
