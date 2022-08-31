package dev.m2en.citation

import dev.kord.common.Color
import dev.kord.common.entity.MessageType
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Icon
import dev.kord.core.entity.Message
import dev.kord.core.entity.channel.TextChannel
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.kordLogger
import dev.kord.rest.builder.message.EmbedBuilder

suspend fun MessageCreateEvent.onQuoteSend() {
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
    targetChannel as TextChannel
    if(targetChannel.isNsfw) {
        kordLogger.error("> **エラー:** ${message.author?.tag} の引用に失敗しました: NSFWとして指定されているチャンネルのメッセージです。")
        return
    }

    val targetMessage = targetChannel.getMessageOrNull(Snowflake(matches.groupValues[3]))
    if(targetMessage == null) {
        message.reply { content = "> **エラー:** メッセージが見つかりません。" }
        kordLogger.error("エラー: ${message.author?.tag} の引用に失敗しました: メッセージが見つかりません。")
        return
    }
    if(!(checkMessageType(targetMessage))) {
        kordLogger.warn("警告: 通常メッセージではないため、引用をキャンセルしました。: ${targetMessage.type}")
        return
    }

    if(targetMessage.embeds.isNotEmpty() && targetMessage.content.isEmpty()) {
        kordLogger.warn("警告: メッセージ内容が空で、Embedのみだったため引用をキャンセルしました。")
        return
    }

    val targetUser = targetMessage.author
    val targetUserName = if(targetMessage.author == null) {
        "不明"
    } else {
        targetMessage.author!!.username
    }

    message.reply { embeds.add(buildEmbed(targetMessage, targetUserName, targetUser?.avatar)) }
    kordLogger.info("引用: ${message.author?.tag} の引用に成功しました: ID - ${targetMessage.id}")
}

private fun buildEmbed(targetMessage: Message, targetUserName: String, targetUserAvatar: Icon?): EmbedBuilder {
    val embed = EmbedBuilder()

    // 添付ファイルの確認
    if(targetMessage.attachments.isNotEmpty()) {
        targetMessage.attachments.forEach{ attachment -> embed.image = attachment.url }
    }

    // アバターが存在するかどうか (存在するなら、authorに設定する)
    if(targetUserAvatar == null) {
        embed.author { name = targetUserName }
    } else {
        embed.author { name = targetUserName; icon = targetUserAvatar.url }
    }

    embed.description = targetMessage.content
    embed.color = Color(160, 232, 210) // 薄緑色
    embed.timestamp = targetMessage.timestamp

    return embed
}

private fun checkMessageType(targetMessage: Message): Boolean {
    if(targetMessage.type != MessageType.Default) {
        if(targetMessage.type != MessageType.Reply) return true
        if(targetMessage.type != MessageType.ThreadStarterMessage) return true

        return false
    }
    return true
}
