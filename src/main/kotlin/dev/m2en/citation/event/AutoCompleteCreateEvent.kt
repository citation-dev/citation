package dev.m2en.citation.event

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class AutoCompleteCreateEvent : ListenerAdapter() {

    private val docsQuery = arrayOf(
        "resources",
        "security-policy",
        "getting-started",
        "troubleshooting",
        "contributing",
        "version1",
        "version2"
    )

    private val githubQuery = arrayOf(
        "code",
        "issues",
        "pulls",
        "discussions",
        "actions",
        "security",
        "pulse"
    )

    override fun onCommandAutoCompleteInteraction(event: CommandAutoCompleteInteractionEvent) {
        if (event.focusedOption.name != "query") return

        when (event.name) {
            "docs" -> {
                event.replyChoiceStrings(docsQuery.filter {
                    it.startsWith(event.focusedOption.value)
                }).queue()
            }

            "github" -> {
                event.replyChoiceStrings(githubQuery.filter {
                    it.startsWith(event.focusedOption.value)
                }).queue()
            }
        }
    }
}
