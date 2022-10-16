package dev.m2en.citation.command

import dev.m2en.citation.utils.BuildEmbed
import dev.m2en.citation.utils.Utils
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction
import net.dv8tion.jda.api.interactions.components.buttons.Button

object ShutdownCommand : ListenerAdapter() {

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (event.name != "shutdown") return

        if (Utils.getEnv("SHUTDOWN_USER_ID") != event.user.id) {
            event.interaction.replyEmbeds(
                BuildEmbed.buildErrorEmbed(
                    "貴方はこのコマンドを実行できません。",
                    "このコマンドを実行できる人は限られています。"
                )
            ).setEphemeral(true).queue()
            return
        }

        val interaction = event.interaction
        when (event.getOption("force")!!.asBoolean) {
            true -> {
                confirmation(
                    interaction,
                    "プロセスを本当に強制終了しますか?",
                    "プロセスの再開は手動で行う必要があります。しない場合はメッセージを閉じてください、\n **警告:** 強制終了すると現在行われている処理がキャンセルされます。(強制終了する必要がない場合は`force: false`で通常終了を行ってください。)"
                )
            }

            else -> {
                interaction.replyEmbeds(
                    BuildEmbed.buildEmbed(
                        "プロセスを終了しました。",
                        "プロセスの再開は手動で行う必要があります。"
                    )
                )
                    .setEphemeral(true).queue()
                event.jda.shutdown()
            }
        }
    }

    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        if (event.interaction.componentId == "shutdown-yes") event.jda.shutdownNow()
    }
}

private fun confirmation(interaction: SlashCommandInteraction, title: String, message: String) {
    interaction.replyEmbeds(
        BuildEmbed.buildEmbed(title, message)
    ).addActionRow(
        Button.danger("shutdown-yes", "同意して終了する"),
    ).setEphemeral(true).queue()
}

