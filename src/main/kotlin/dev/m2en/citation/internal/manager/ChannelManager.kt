package dev.m2en.citation.internal.manager

import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.channel.ChannelType
import net.dv8tion.jda.api.entities.channel.concrete.ForumChannel
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel

class ChannelManager {

    companion object {

        /**
         * チャンネルを取得する
         *
         * @param guild 検索対象のギルド
         * @param id 検索対象のチャンネルID
         * @return メッセージリンクのチャンネル
         */
        fun getChannel(guild: Guild, id: String): GuildMessageChannel {
            val channel = guild.getGuildChannelById(id)
                ?: throw IllegalArgumentException("チャンネルを取得することができませんでした")

            if (!checkChannelType(channel)) {
                throw RuntimeException("チャンネルタイプが不正です")
            }

            if(isNSFW(channel)) {
                throw RuntimeException("NSFWチャンネルのメッセージは引用できません。")
            }

            return channel as GuildMessageChannel
        }

        /**
         * チャンネルタイプが利用できるか確認する
         *
         * @param channel 検査対象のチャンネル
         * @return true -> 利用可能, false -> 利用できない
         */
        private fun checkChannelType(channel: GuildChannel): Boolean {
            return when (channel) {
                is TextChannel, is ThreadChannel, is ForumChannel, is VoiceChannel -> true
                else -> false
            }
        }

        /**
         * NSFWチャンネルかどうかを確認する
         *
         * @param channel 検査対象のチャンネル
         * @return true -> NSFW, false -> 非NSFW
         */
        private fun isNSFW(channel: GuildChannel): Boolean {
            return when (channel) {
                is TextChannel -> channel.isNSFW
                is ForumChannel -> channel.isNSFW
                is VoiceChannel -> channel.isNSFW
                is ThreadChannel -> {
                    if(channel.parentChannel.type == ChannelType.FORUM) {
                        return channel.parentChannel.asForumChannel().isNSFW
                    }
                    return channel.parentChannel.asTextChannel().isNSFW
                }
                else -> false
            }
        }
    }
}
