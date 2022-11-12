// -----------------------------------------
// Created by m2en : Processing Help Command
// -----------------------------------------

package dev.m2en.citation.api.command

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.buttons.Button

private const val github = "https://github.com/citation-dev/citation"
private const val docs = "https://citation.m2en.dev"

class HelpCommand(private val tag: String) : ListenerAdapter() {

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (event.name != "help") return

        event.replyEmbeds(buildHelpEmbed(tag))
            .setActionRow(
                Button.link(docs, "Document"),
                Button.link("$github/issues/new/choose", "Issue"),
                Button.link("$github/discussions", "Discussion")
            )
            .setEphemeral(true).queue()
    }
}

private fun buildHelpEmbed(tag: String): MessageEmbed {
    return EmbedBuilder().apply {
        setTitle("ヘルプ", github)
        setAuthor(tag)
        setDescription("✨ Discord Bot to show MessageLink's preview")
        addField(
            "引用",
            "メッセージリンクを送信するとそのメッセージをEmbedとして送信します。`<>` でリンクを囲えば、リンクだけを送ることができます。\n${
                markdownLink(
                    "$docs/reference/features/citation"
                )
            }",
            false
        )
        addField(
            "コマンド",
            "引用以外の機能はスラッシュコマンドで利用できます。\n${markdownLink("$docs/reference/features")}",
            false
        )
    }.build()
}

/**
 * 埋め込み上で使えるマークダウンリンクを作成します。
 *
 * @param url URL
 * @param name マークダウンリンクの名前
 * @return マークダウンリンク
 */
private fun markdownLink(url: String): String {
    return "[詳しくはこちら]($url)"
}
