package com.github.emiliero.echelon.commands

import discord4j.core.DiscordClient
import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.MessageChannel
import discord4j.core.`object`.entity.User
import discord4j.core.event.domain.message.MessageCreateEvent
import java.awt.Color

fun printHelp(client: DiscordClient) {
    client.eventDispatcher.on(MessageCreateEvent::class.java)
        .map { obj: MessageCreateEvent -> obj.message }
        .filter { message: Message ->
            message.author.map { user: User -> !user.isBot}.orElse(false)
        }
        .filter { message: Message ->
            message.content.orElse("").contains(CommonCommands.Help.commandString, ignoreCase = true)
        }
        .flatMap { m : Message -> m.channel }
        .flatMap<Message> { channel: MessageChannel ->
            channel.createMessage { messageCreateSpec ->
                messageCreateSpec.setEmbed { embedCreateSpec ->
                    embedCreateSpec
                        .setTitle("Commands")
                        .setColor(Color.decode("#5EB0AE"))
                        .setDescription("An overview of the commands available for Echelon.")
                        .addField(
                            "Common commands",
                            printCommonCommands(),
                            false)
                        .addField(
                            "Administrative commands",
                            printAdminCommands(),
                            false)
                }
            }
        }
        .subscribe()
}

private fun printCommonCommands() : String {
    var result : String = "_Commands available for every server member:_\n"

    for (enum in CommonCommands.values()) {
        result += "`${enum.commandString}` -  $enum\n"
    }

    return result
}

private fun printAdminCommands() : String {
    var result : String = "_The following commands are only available for lecturers and student assistants:_\n"

    for (enum in AdminCommands.values()) {
        result += "`${enum.commandString}` -  $enum\n"
    }

    return result
}
