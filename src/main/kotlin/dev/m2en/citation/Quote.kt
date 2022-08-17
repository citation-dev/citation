package dev.m2en.citation

import dev.kord.common.Color
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Message
import dev.kord.core.entity.User
import dev.kord.core.entity.channel.GuildMessageChannel
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.kordLogger
import dev.kord.rest.builder.message.EmbedBuilder

suspend fun MessageCreateEvent.onQuote() {
    // GuildId/ChannelId/MessageId
    val linkRegex = Regex("""https://(?:ptb\.|canary\.)?discord(?:app)?.com/channels/(\d+)/(\d+)/(\d+)""")
    // <...GuildId/ChannelId/MessageId>
    val skipRegex = Regex("""<.*?>""")

    var str = message.content
    str = str.replace(skipRegex, "")
    if(!(linkRegex.containsMatchIn(str))) {
        return
    }

    val matches = linkRegex.find(str) ?: return
    if(Snowflake(matches.groupValues[1]) != message.getGuild().id) {
        kordLogger.warn("警告: ${message.author?.tag} の引用をスキップしました: ギルドが一致しません。")
        return
    }

    val targetChannel = message.getGuild().getChannelOrNull(Snowflake(matches.groupValues[2]))
    if(targetChannel == null) {
        kordLogger.error("> **エラー:** ${message.author?.tag} の引用に失敗しました: チャンネルが見つかりません。")
        return
    }
    targetChannel as GuildMessageChannel

    val targetMessage = targetChannel.getMessageOrNull(Snowflake(matches.groupValues[3]))
    if(targetMessage == null) {
        message.reply { content = "> **エラー:** メッセージが見つかりません。" }
        kordLogger.error("エラー: ${message.author?.tag} の引用に失敗しました: メッセージが見つかりません。")
        return
    }

    val targetUser = targetMessage.author
    if(targetUser == null) {
        message.reply { content = "> **エラー:** ユーザーが見つかりません。" }
        kordLogger.error("エラー: ${message.author?.tag} の引用に失敗しました: メッセージのユーザーを取得することが出来ませんでした。")
        return
    }

    message.reply { embeds.add(buildEmbed(targetMessage, targetUser)) }
    kordLogger.info("引用: ${message.author?.tag} の引用に成功しました: ID - ${targetMessage.id}")
}

private fun buildEmbed(targetMessage: Message, targetUser: User): EmbedBuilder {
    val embed = EmbedBuilder()

    // 添付ファイルの確認
    if(targetMessage.attachments.isNotEmpty()) {
        targetMessage.attachments.forEach{ attachment -> embed.image = attachment.url }
    }

    // アバターが存在するかどうか (存在するなら、authorに設定する)
    if(targetUser.avatar == null) {
        embed.author { name = targetUser.username }
    } else {
        embed.author { name = targetUser.username; icon = targetUser.avatar!!.url }
    }

    embed.description = targetMessage.content
    embed.color = Color(160, 232, 210) // 薄緑色
    embed.timestamp = targetMessage.timestamp

    return embed
}
