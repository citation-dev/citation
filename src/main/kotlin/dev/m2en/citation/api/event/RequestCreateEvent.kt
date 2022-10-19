package dev.m2en.citation.api.event

import dev.m2en.citation.internal.manager.ChannelManager
import dev.m2en.citation.internal.manager.MessageManager
import dev.m2en.citation.internal.utils.EmbedBuilder
import dev.m2en.citation.internal.utils.Logger
import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.buttons.Button

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
        val author = message.author
        if (author.isSystem || author.isBot) return

        var content = message.contentRaw
        content = content.replace(skipRegex, "")
        if (!linkRegex.containsMatchIn(content)) return

        val guild = message.guild
        val snowflake = getId(linkRegex.find(message.contentRaw) ?: return)

        // リクエスト元ギルドと引用ギルドが一致しているかどうか確認
        if (guild.id != snowflake.first) return

        val targetChannel = ChannelManager.getChannel(guild, snowflake.second)

        val targetMessage = MessageManager.getMessage(targetChannel, snowflake.third)

        val authorName = message.author.name

        message.replyEmbeds(EmbedBuilder.buildQuoteEmbed(targetMessage, authorName)).addActionRow(
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
