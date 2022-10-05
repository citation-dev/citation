package dev.m2en.citation.message

import dev.kord.common.entity.ChannelType
import dev.kord.common.entity.MessageType
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.getChannelOfOrNull
import dev.kord.core.behavior.reply
import dev.kord.core.entity.*
import dev.kord.core.entity.channel.*
import dev.kord.core.entity.channel.thread.ThreadChannel
import dev.kord.rest.builder.message.EmbedBuilder
import dev.m2en.citation.handler.MessageHandler
import dev.m2en.citation.utils.Logger
import dev.m2en.citation.utils.Utils

object QuoteSendListener : MessageHandler {
    override suspend fun canProcess(message: Message): Boolean = message.author?.isBot == false

    override suspend fun messageHandle(message: Message) {

        /**
         * メッセージリンクを取り出す正規表現
         * GuildId/ChannelId/MessageId
         */
        val linkRegex = Regex("""https://(?:ptb\.|canary\.)?discord(?:app)?.com/channels/(\d+)/(\d+)/(\d+)""")

        /**
         * スキップ対象のメッセージリンクを取り出す正規表現
         * e.x) <https://discord.com/channels/1025777155604496515/1025777155604496515/1025777155604496515>
         */
        val skipLinkRegex = Regex("""<.*?>""")

        var content = message.content
        content = content.replace(skipLinkRegex, "")
        if(!(linkRegex.containsMatchIn(content))) {
            return
        }

        val requester = message.author?.fetchMember(message.getGuild().id) ?: throw Error("リクエスト送信者を見つけることができませんでした。")

        val quoteMessage = getQuoteMessage(message, linkRegex, requester)

        val replyMessage = message.reply { embeds.add(buildEmbed(quoteMessage, requester.username)) }
        replyMessage.addReaction(ReactionEmoji.Unicode("\uD83D\uDDD1"))
        Logger.sendInfo("${requester.username} のリクエストを承認しました。(引用メッセージID: ${replyMessage.id})")

    }

    /**
     * 引用対象のメッセージを検索します。
     *
     * @param request 引用リクエストのメッセージ
     * @return 引用対象のメッセージ
     */
    private suspend fun getQuoteMessage(request: Message, linkRegex: Regex, requester: Member): Message {

        val requestGuild = request.getGuild()

        val requesterDisplayName = Utils.createUserDisplayName(requester.username, requester.id, false)

        if(requester.isBot) {
            throw Error("Botのリクエストをブロックしました。<リクエスト送信者: $requesterDisplayName>")
        }

        // ID群の取り出し
        val matches = linkRegex.find(request.content)
            ?: throw Error("IDが不正のリクエストを拒否しました。<リクエスト送信者: $requesterDisplayName>")

        if(Snowflake(matches.groupValues[1]) != requestGuild.id) {
            throw Error("別ギルドのメッセージの引用リクエストを拒否しました。<リクエスト送信者: $requesterDisplayName>")
        }

        // チャンネルの検索、及び検査
        val channel = requestGuild.getChannelOfOrNull<GuildMessageChannel>(Snowflake(matches.groupValues[2]))
            ?: throw Error("チャンネルが見つかりませんでした。")

        if(!(channel.data.nsfw.discordBoolean)) {
            if(isChannelNsfw(channel)) {
                throw Error("NSFWのメッセージ引用リクエストをブロックしました。<リクエスト送信者: $requesterDisplayName>")
            }
        }

        // メッセージの取得
        val message = channel.getMessageOrNull(Snowflake(matches.groupValues[3]))
            ?: throw Error("メッセージが見つかりませんでした。")

        if(!checkMessageType(message)) {
            throw Error("メッセージステータスまたはタイプが不正です。<リクエスト送信者: $requesterDisplayName>")
        }

        return message
    }

    /**
     * メッセージタイプと引用すべきメッセージなのかを確認する。
     *
     * @return citationが引用できるメッセージタイプであればTrue, なければFalse
     */
    private fun checkMessageType(message: Message): Boolean {
        if(message.embeds.isNotEmpty() && message.content.isEmpty()) {
            return false
        }

        return when(message.type) {
            MessageType.Default, MessageType.Reply, MessageType.ThreadStarterMessage -> {
                true
            }

            else -> {
                false
            }
        }
    }

    /**
     * 引数て指定されたGuildMessageChannelがNSFWではないかの確認を行う。
     *
     * @param channel 検査対象のチャンネル
     * @return NSFWであればTrue, なければFalse
     */
    private suspend fun isChannelNsfw(channel: GuildMessageChannel): Boolean {
        if(channel.data.nsfw.discordBoolean) {
            return true
        }

        when(channel.type) {
            ChannelType.PublicGuildThread, ChannelType.PrivateThread, ChannelType.PublicNewsThread -> {
                channel as ThreadChannel
                if(channel.getParent().data.nsfw.discordBoolean) {
                    return true
                }
                return false
            }
            else -> {
                return false
            }
        }
    }


    /**
     * 埋め込みを生成する
     *
     * @param message 引用メッセージ
     * @param requesterName リクエスト送信者の名前
     * @return 引用結果(Embed)を返す。
     */
    private suspend fun buildEmbed(message: Message, requesterName: String): EmbedBuilder {
        val messageAuthor = message.author?.fetchMember(message.getGuild().id) ?: throw Error("埋め込みの生成に失敗しました。")

        val embed = EmbedBuilder().apply {
            description = message.content
            color = messageAuthor.accentColor
            timestamp = message.timestamp
            footer {
                text = "リクエスト送信者: $requesterName"
            }

            if(message.editedTimestamp !== null) {
                timestamp = message.editedTimestamp
            }

            if(messageAuthor.avatar !== null) {
                author { name = messageAuthor.username; icon = messageAuthor.avatar?.url }
            } else {
                author { name = messageAuthor.username }
            }

            // 添付ファイルの確認
            if(message.attachments.isNotEmpty()) {
                message.attachments.forEach { attachment ->
                    if(!(attachment.isImage)) return@forEach

                    if(attachment.description !== null) {
                        field {
                            name = "ALTテキスト"
                            value = attachment.description.toString()
                            inline = true
                        }
                    }

                    if(attachment.isSpoiler) {
                        field {
                            name = "スポイラー済みの添付ファイル"
                            value = "[${attachment.filename}](${attachment.url})"
                            inline = true
                        }
                        return@forEach
                    }

                    image = attachment.url
                    field {
                        name = "添付ファイル"
                        value = attachment.filename
                        inline = true
                    }
                }
            }
        }

        return embed
    }
}
