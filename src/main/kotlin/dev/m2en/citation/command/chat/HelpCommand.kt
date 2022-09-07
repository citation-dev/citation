package dev.m2en.citation.command.chat

import dev.kord.core.behavior.interaction.response.DeferredEphemeralMessageInteractionResponseBehavior
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.entity.interaction.GuildChatInputCommandInteraction
import dev.kord.rest.builder.message.EmbedBuilder
import dev.m2en.citation.command.InteractionCommandInterface
import dev.m2en.citation.getCitationVersion

object HelpCommand: InteractionCommandInterface {

    override suspend fun onCommand(interaction: GuildChatInputCommandInteraction, responseBehavior: DeferredEphemeralMessageInteractionResponseBehavior) {
        val referenceLink = "https://citation.m2en.dev/reference/"

        val embed = EmbedBuilder().apply {
            title = "ヘルプ - v${getCitationVersion()}"
            url = "https://github.com/m2en/citation/releases/latest"
            description = "citationはメッセージリンクのプレビューを表示するDiscord Botです"
            field {
                name = "引用"
                value = "citationに閲覧権限が付与されているチャンネル内でメッセージリンクを送信すると、そのメッセージリンクのレビューをEmbedとして送信します。\n" +
                        "リンクを `<>` で囲うと引用せずにスルーさせることができます。\n" +
                        "(スレッド内でも、プライベートで招待されていないなどの一連の条件を除き、自動で引用します)\n" +
                        "[詳しくはこちら](${referenceLink + "quote"})"
            }
            field {
                name = "メッセージデバック"
                value = "メッセージを右クリックして出てくるメニュー(モバイル版はメッセージ長押し)からメッセージをコードブロックとして表示するデバック機能を利用できます。\n" +
                        "[詳しくはこちら](${referenceLink + "message-debug"})"
            }
            field {
                name = "リンク"
                value = "・[リポジトリ](https://github.com/m2en/citation)\n" +
                        "・[公式ドキュメント](https://citation.m2en.dev/)"
            }
            footer {
                text = "${interaction.user.username} - citation by m2en"
            }
        }

        responseBehavior.respond { embeds = MutableList(1) { embed } }
    }
}
