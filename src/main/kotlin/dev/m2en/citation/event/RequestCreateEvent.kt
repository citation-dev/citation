package dev.m2en.citation.event

import dev.m2en.citation.utils.BuildEmbed
import dev.m2en.citation.utils.Logger
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageType
import net.dv8tion.jda.api.entities.channel.ChannelType
import net.dv8tion.jda.api.entities.channel.concrete.ForumChannel
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel
import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.buttons.Button
import net.dv8tion.jda.internal.utils.PermissionUtil

class RequestCreateEvent : ListenerAdapter() {

    /**
     * メッセージリンクを取り出す正規表現
     * GuildId/ChannelId/MessageId
     */
    private val linkRegex =
        Regex("""https://(?:ptb\.|canary\.)?discord(?:app)?.com/channels/(\d+)/(\d+)/(\d+)""")

    /**
     * スキップ対象のメッセージリンクを取り出す正規表現
     * e.x) <https://discord.com/channels/1025777155604496515/1025777155604496515/1025777155604496515>
     */
    private val skipRegex = Regex("""<.*?>""")

    override fun onMessageReceived(event: MessageReceivedEvent) {
        val message = event.message
        var content = message.contentRaw
        content = content.replace(skipRegex, "")
        if (!linkRegex.containsMatchIn(content)) return

        val guild = message.guild
        val snowflake = getId(linkRegex.find(message.contentRaw) ?: return)

        // リクエスト元ギルドと引用ギルドが一致しているかどうか確認
        if (guild.id != snowflake.first) return

        val targetChannel = getChannel(guild, snowflake.second)
        if (isNSFW(targetChannel)) return

        val targetMessage = getMessage(targetChannel, snowflake.third)

        val authorName = message.author.name

        message.replyEmbeds(BuildEmbed.buildQuoteEmbed(targetMessage, authorName)).addActionRow(
            Button.link(targetMessage.jumpUrl, Emoji.fromUnicode("\uD83D\uDCAC")), // メッセージへのジャンプボタン
            Button.link(targetChannel.jumpUrl, Emoji.fromUnicode("#️⃣")) // チャンネルへのジャンプボタン
        ).queue()

        Logger.sendInfo("$authorName の引用に成功しました")
    }

}

/**
 * リンクからIDを取り出す
 *
 * @param matches 正規表現の検査結果
 * @return 各ID (first: ギルドID, second: チャンネルID, third: メッセージID)
 */
private fun getId(matches: MatchResult): Triple<String, String, String> {
    val guildId = matches.groupValues[1]
    val channelId = matches.groupValues[2]
    val messageId = matches.groupValues[3]
    return Triple(guildId, channelId, messageId)
}

// ==========================================
// チャンネル取得
// ==========================================

/**
 * チャンネルを取得する
 *
 * @param guild 検索対象のギルド
 * @param id 検索対象のチャンネルID
 * @return メッセージリンクのチャンネル
 */
private fun getChannel(guild: Guild, id: String): GuildMessageChannel {
    val channel = guild.getGuildChannelById(id)
        ?: throw IllegalArgumentException("チャンネルを取得することができませんでした")

    if (!checkChannelType(channel)) {
        throw RuntimeException("チャンネルタイプが不正です")
    }

    return channel as GuildMessageChannel
}

/**
 * チャンネルタイプの確認
 *
 * @param channel 検査対象のチャンネル
 * @return チャンネル
 */
private fun checkChannelType(channel: GuildChannel): Boolean {
    return when (channel) {
        is TextChannel, is ThreadChannel, is ForumChannel, is VoiceChannel -> true
        else -> false
    }
}

/**
 * NSFWチャンネルかどうかの確認
 *
 * @param channel 検査対象のチャンネル
 * @return true -> NSFW, false -> 非NSFW
 */
private fun isNSFW(channel: GuildMessageChannel): Boolean {
    return when (channel) {
        is TextChannel -> channel.isNSFW
        is ForumChannel -> channel.isNSFW
        is ThreadChannel -> channel.parentChannel.asTextChannel().isNSFW
        is VoiceChannel -> channel.isNSFW
        else -> false
    }
}

/**
 * メッセージを取得する
 *
 * @param channel 検索対象のチャンネル
 * @param id 検索に使用するID
 */
private fun getMessage(channel: GuildMessageChannel, id: String): Message {
    val message = channel.retrieveMessageById(id).complete()
        ?: throw IllegalArgumentException("メッセージを取得することができませんでした")

    if (!checkMessageType(message)) {
        throw RuntimeException("メッセージタイプが不正です")
    }

    return message
}

private fun checkMessageType(message: Message): Boolean {
    if (message.embeds.isNotEmpty() && message.contentRaw.isEmpty()) return false
    return when (message.type) {
        // 通常メッセージ、返信、スラッシュコマンド、コンテキストメニュー、スレッドの初期メッセージのみを受け入れる
        MessageType.DEFAULT, MessageType.INLINE_REPLY,
        MessageType.SLASH_COMMAND, MessageType.THREAD_STARTER_MESSAGE,
        MessageType.CONTEXT_COMMAND -> true

        else -> false
    }
}
