package dev.m2en.citation.command

import dev.m2en.citation.utils.Utils
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.buttons.Button

private const val releseTag = "2022.2.1"
private const val github = "https://github.com/m2en/citation"
private const val docs = "https://citation.m2en.dev"

class HelpCommand(private val tag: String) : ListenerAdapter() {

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (event.name != "help") return

        event.interaction
            .replyEmbeds(buildHelpEmbed(tag))
            .setActionRow(
                Button.link(docs, "ドキュメント"),
                Button.link("$github/issues/new/choose", "Issue"),
                Button.link("$github/discussions", "Discussion")
            )
            .setEphemeral(true).queue()
    }
}

private fun buildHelpEmbed(tag: String): MessageEmbed {
    val quote = Utils.markdownLink("$docs/version2/quote/", "詳しくはこちら")
    val command = Utils.markdownLink("$docs/version2/command/", "詳しくはこちら")
    return EmbedBuilder().apply {
        setTitle("ヘルプ", github)
        setAuthor("$tag ($releseTag)")
        setDescription("✨ Discord Bot to show MessageLink's preview")
        addField(
            "引用",
            "メッセージリンクを送信するとそのメッセージをEmbedとして送信します。`<>` でリンクを囲えば、リンクだけを送ることができます。\n$quote",
            false
        )
        addField(
            "コマンド",
            "引用以外の機能はスラッシュコマンドで利用できます。\n$command",
            false
        )
        addField(
            "ドキュメント",
            "citationの使い方は **citation docs** を確認してください。(`/docs`で検索もできます)",
            false
        )
    }.build()
}
