package dev.m2en.citation

import dev.kord.common.Color
import dev.kord.common.entity.ChannelType
import dev.kord.common.entity.MessageType
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.getChannelOf
import dev.kord.core.behavior.getChannelOfOrNull
import dev.kord.core.behavior.reply
import dev.kord.core.entity.Icon
import dev.kord.core.entity.Message
import dev.kord.core.entity.ReactionEmoji
import dev.kord.core.entity.User
import dev.kord.core.entity.channel.*
import dev.kord.core.entity.channel.thread.ThreadChannel
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.kordLogger
import dev.kord.rest.builder.message.EmbedBuilder

suspend fun MessageCreateEvent.onQuoteSend(reactionEmoji: ReactionEmoji.Unicode) {
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

    val targetChannel = message.getGuild().getChannelOfOrNull<GuildMessageChannel>(Snowflake(matches.groupValues[2]))
    val messageChannelData = message.getGuild().getChannelOf<GuildMessageChannel>(message.channelId)
    if(targetChannel == null) {
        kordLogger.error("エラー: ${message.author?.tag} の引用に失敗しました: チャンネルが見つかりません。")
        return
    }
    if(isChannelNsfw(targetChannel, messageChannelData)) {
        kordLogger.error("エラー: ${message.author?.tag} の引用に失敗しました: NSFWとして指定されているチャンネルのメッセージです。")
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


    val replyMessage = message.reply { embeds.add(buildEmbed(targetMessage, targetUserName, targetUser?.avatar, message.author)) }
    replyMessage.addReaction(reactionEmoji)
    kordLogger.info("引用: ${message.author?.tag} の引用に成功しました: ID - ${targetMessage.id}")
}

/**
 * 埋め込みを生成する
 *
 * @return 引用結果(Embed)を返す。
 */
private fun buildEmbed(targetMessage: Message, targetUserName: String, targetUserAvatar: Icon?, quoteOwner: User?): EmbedBuilder {
    val embed = EmbedBuilder().apply {
        if(targetMessage.attachments.isNotEmpty()) {
            targetMessage.attachments.forEach{ attachment -> image = attachment.url }
        }

        if(targetUserAvatar == null) {
            author { name = targetUserName }
        } else {
            author { name = targetUserName; icon = targetUserAvatar.url }
        }

        if (quoteOwner != null) {
            if(quoteOwner.avatar == null) {
                footer { text = quoteOwner.username; }
            } else {
                footer { text = quoteOwner.username; icon = quoteOwner.avatar!!.url }
            }
        }

        description = targetMessage.content
        color = Color(160, 232, 210) // 薄緑色
        timestamp = targetMessage.timestamp
    }

    return embed
}

/**
 * メッセージタイプを確認する。
 *
 * @return citationが引用できるメッセージタイプであればTrue, なければFalse
 */
private fun checkMessageType(targetMessage: Message): Boolean {
    if(targetMessage.type != MessageType.Default) {
        if(targetMessage.type != MessageType.Reply) return true
        if(targetMessage.type != MessageType.ThreadStarterMessage) return true

        return false
    }
    return true
}

/**
 * 引数て指定されたGuildMessageChannelがNSFWではないかの確認を行う。
 * (同時に引用が行われたチャンネルも確認し、NSFWでなければFalseを戻す)
 *
 * @return NSFWであればTrue, なければFalse
 */
private suspend fun isChannelNsfw(targetChannel: GuildMessageChannel, messageChannel: GuildMessageChannel): Boolean {
    if(messageChannel.data.nsfw.discordBoolean) {
        return false
    }

    if(targetChannel.type == ChannelType.PublicGuildThread || targetChannel.type == ChannelType.PrivateThread || targetChannel.type == ChannelType.PublicNewsThread) {
        targetChannel as ThreadChannel
        val parentChannel = targetChannel.getParent()
        if(parentChannel.data.nsfw.discordBoolean) {
            return true
        }
    }

    if (targetChannel.data.nsfw.discordBoolean) {
        return true
    }

    return false
}
