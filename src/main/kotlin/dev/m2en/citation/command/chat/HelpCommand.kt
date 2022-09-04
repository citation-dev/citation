package dev.m2en.citation.command.chat

import dev.kord.core.behavior.interaction.response.DeferredEphemeralMessageInteractionResponseBehavior
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.entity.interaction.GuildChatInputCommandInteraction
import dev.kord.rest.builder.message.EmbedBuilder
import dev.m2en.citation.command.InteractionCommandInterface
import dev.m2en.citation.getCitationVersion

object HelpCommand: InteractionCommandInterface {

    override suspend fun onCommand(interaction: GuildChatInputCommandInteraction, responseBehavior: DeferredEphemeralMessageInteractionResponseBehavior) {
        val embed = EmbedBuilder().apply {
            title = "ヘルプ - v${getCitationVersion()}"
            url = "https://github.com/m2en/citation"
            description = "citationはメッセージリンクのプレビューを表示するDiscord Botです"
            field {
                name = "引用"
                value = "citationに閲覧権限が付与されているチャンネル内でメッセージリンクを送信すると、そのメッセージリンクのレビューをEmbedとして送信します。\n" +
                        "(スレッド内でも、プライベートで招待されていないなどの一連の条件を除き、自動で引用します)\n" +
                        "[詳しくはこちら](https://github.com/m2en/citation/blob/main/docs/function/quote.md)"
            }
            field {
                name = "引用スキップ"
                value =  "リンクを `<>` で囲うと引用をスキップします"
            }
            field {
                name = "メッセージデバック"
                value = "メッセージを右クリックして出てくるメニュー(モバイル版はメッセージ長押し)からメッセージをコードブロックとして表示するデバック機能を利用できます。\n" +
                        "[詳しくはこちら](https://github.com/m2en/citation/blob/main/docs/function/debug.md)"
            }
            field {
                name = "リンク"
                value = "・[リポジトリ](https://github.com/m2en/citation)\n" +
                        "・[公式ドキュメント](https://github.com/m2en/citation/blob/main/docs/README.md)"
            }
            footer {
                text = "${interaction.user.username} - citation by m2en"
            }
        }

        responseBehavior.respond { embeds = MutableList(1) { embed } }
    }
}
