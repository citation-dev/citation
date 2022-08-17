package dev.m2en.citation.command

import dev.kord.core.behavior.interaction.response.DeferredEphemeralMessageInteractionResponseBehavior
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.entity.interaction.GuildChatInputCommandInteraction
import dev.kord.rest.builder.message.EmbedBuilder

object HelpCommand: InteractionCommandInterface {

    override suspend fun onCommand(interaction: GuildChatInputCommandInteraction, responseBehavior: DeferredEphemeralMessageInteractionResponseBehavior) {
        val embed = EmbedBuilder()
        embed.title = "ヘルプ"
        embed.url = "https://github.com/m2en/citation"
        embed.description = "citationはメッセージリンクのプレビューを表示するDiscord Botです"
        embed.color
        embed.field("引用") {
            "citationに閲覧権限が付与されているチャンネル内でメッセージリンクを送信すると、そのメッセージリンクのレビューをEmbedとして送信します。\n" +
            "(スレッド内でも、プライベートで招待されていないなどの一連の条件を除き、自動で引用します)"
        }
        embed.field("引用スキップ") {
            "リンクを `<>` で囲うと引用をスキップします"
        }
        embed.field("メッセージデバック") {
            "/debugで指定したメッセージをコードブロックとして送信します。指定したメッセージがあるチャンネル内で実行する必要があります。"
        }
        embed.field("リンク") {
            "・[リポジトリ](https://github.com/m2en/citation)\n" +
            "・[公式ドキュメント](https://github.com/m2en/citation/tree/main/docs)"
        }
        embed.footer{
            text = "${interaction.user.username} - citation by m2en"
        }

        responseBehavior.respond { embeds = MutableList(1) { embed } }
    }
}
