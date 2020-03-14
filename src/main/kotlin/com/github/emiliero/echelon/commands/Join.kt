package com.github.emiliero.echelon.commands

import discord4j.core.DiscordClient
import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.MessageChannel
import discord4j.core.`object`.entity.User
import discord4j.core.event.domain.message.MessageCreateEvent

fun Join(client: DiscordClient) {
    client.eventDispatcher.on(MessageCreateEvent::class.java)
        .map { obj: MessageCreateEvent -> obj.message }
        .filter { message: Message ->
            message.author.map { user: User -> !user.isBot }.orElse(false)
        }
        .filter { message: Message ->
            message.content.orElse("").contains("!join", ignoreCase = true)
        }
        .flatMap<MessageChannel> { obj: Message -> obj.channel }
        .flatMap<Message> { channel: MessageChannel -> channel.createMessage("You've been added to the queue!") }
        .subscribe()
}