package dev.m2en.citation.api.command

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction
import net.dv8tion.jda.api.interactions.components.buttons.Button
import net.dv8tion.jda.api.requests.RestAction


class PingCommand: ListenerAdapter() {

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if(event.name != "ping") return

        val jda = event.jda

        jda.restPing.queue {
            event.replyEmbeds(buildEmbed(it, jda.gatewayPing)).setEphemeral(true).queue()
        }
    }

    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        if(event.componentId != "ping-hint") return

        event.reply(hint)
    }

    private fun buildEmbed(restPing: Long, gatewayPing: Long): MessageEmbed {
        return EmbedBuilder().apply {
            setTitle("Ping")
            setDescription("Pong! (これらはおおよその値です。)\n[Discord Status](https://discordstatus.com/)")
            setColor(0x7e56c2)
            addField("Rest", "**$restPing** ms", true)
            addField("Gateway", "**$gatewayPing** ms", true)
            addField("Hint:", hint, false)
        }.build()
    }

    private val hint = """
        > - Rest PingはDiscord APIがREST requestに応答するまでにかかった時間を表してます。
        > - Gateway PingはDiscord APIが最後のハートビートに応答するまでにかかった時間(アクティブなセッションのWebSocket Ping)を表しています。
        > - Pingに関する詳しい情報は [ドキュメント](https://citation.m2en.dev/version2/command-overview/#ping) を参照してください。
        > - citationのレスポンスが悪い場合は [Discord Status](https://discordstatus.com/) の **API Response Time** を確認してください。 
    """.trimIndent()
}
