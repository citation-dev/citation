package dev.m2en.citation.api.command

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction
import net.dv8tion.jda.api.interactions.components.buttons.Button

private const val docsLink = "https://citation.m2en.dev"
private const val githubLink = "https://github.com/m2en/citation"

object QueryCommand : ListenerAdapter() {

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if(event.name != "query") return

        val option = event.getOption("query")!!.asString
        when (event.name) {
            "docs" -> searchDocs(event, option)
            "github" -> searchGitHub(event, option)
        }
    }
}

private fun searchDocs(interaction: SlashCommandInteraction, query: String) {
    when (query) {
        "version1", "version2" -> {
            replyRecruit(interaction, query, "$docsLink/$query")
        }

        "resources" -> {
            replyRecruit(interaction, query, "$docsLink/$query")
        }

        else -> {
            replyRecruit(interaction, query, "$docsLink/resource/$query")
        }
    }
}

private fun searchGitHub(interaction: SlashCommandInteraction, query: String) {
    when (query) {
        "code" -> replyRecruit(interaction, "Code", githubLink)
        "issues" -> replyRecruit(interaction, "Issues", "$githubLink/$query")
        "pulls" -> replyRecruit(interaction, "Pull requests", "$githubLink/$query")
        "discussions" -> replyRecruit(interaction, "Discussions", "$githubLink/$query")
        "actions" -> replyRecruit(interaction, "GitHub Actions", "$githubLink/$query")
        "security" -> replyRecruit(interaction, "Security overview", "$githubLink/$query")
        "pulse" -> replyRecruit(interaction, "Insights", "$githubLink/$query")
    }
}

private fun replyRecruit(interaction: SlashCommandInteraction, title: String, url: String) {
    val embed = EmbedBuilder().apply {
        setTitle(title)
        setDescription("🪄 $url")
        setColor(0x9bebd7)
        setAuthor("検索結果")
    }.build()

    return interaction.replyEmbeds(embed)
        .addActionRow(Button.link(url, title))
        .setEphemeral(true)
        .queue()
}
