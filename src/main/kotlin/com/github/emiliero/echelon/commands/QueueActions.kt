package com.github.emiliero.echelon.commands

import com.github.emiliero.echelon.queue.ListActionsArray.addPersonToQueue
import com.github.emiliero.echelon.queue.ListActionsArray.printList
import com.github.emiliero.echelon.queue.ListActionsArray.removePersonFromQueue
import discord4j.core.DiscordClient
import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.MessageChannel
import discord4j.core.`object`.entity.User
import discord4j.core.event.domain.message.MessageCreateEvent

fun queueActions(client: DiscordClient) {
    joinQueue(client)
    leaveQueue(client)
    printQueue(client)
}

private fun joinQueue(client: DiscordClient) {
    var username: String = ""
    var discriminator: String = ""

    client.eventDispatcher.on(MessageCreateEvent::class.java)
        .map { obj: MessageCreateEvent -> obj.message }
        .filter { message: Message ->
            message.author.map { user: User -> !user.isBot }.orElse(false)
        }
        .filter { message: Message ->
            message.content.orElse("").contains(Commands.Join.commandString, ignoreCase = true)
        }.flatMap { m: Message ->
            username = m.author.get().username
            discriminator = m.author.get().discriminator
            m.channel
        }
        .flatMap<Message> { channel: MessageChannel ->
            channel.createMessage(
                addPersonToQueue(
                    username,
                    discriminator
                )
            )
        }
        .subscribe()
}

private fun leaveQueue(client: DiscordClient) {
    var username : String = ""
    var discriminator : String = ""

    client.eventDispatcher.on(MessageCreateEvent::class.java)
        .map { obj: MessageCreateEvent -> obj.message }
        .filter { message: Message ->
            message.author.map { user: User -> !user.isBot }.orElse(false)
        }
        .filter { message: Message ->
            message.content.orElse("").contains(Commands.Leave.commandString, ignoreCase = true)
        }
        .flatMap { m : Message ->
            username = m.author.get().username
            discriminator = m.author.get().discriminator
            m.channel
        }
        .flatMap<Message> { channel: MessageChannel -> channel.createMessage(removePersonFromQueue(username, discriminator))}
        .subscribe()
}

private fun printQueue(client: DiscordClient){
    client.eventDispatcher.on(MessageCreateEvent::class.java)
        .map { obj: MessageCreateEvent -> obj.message }
        .filter { message: Message ->
            message.author.map { user: User -> !user.isBot}.orElse(false)
        }
        .filter { message: Message ->
            message.content.orElse("").contains(Commands.PrintQueue.commandString, ignoreCase = true)
        }.flatMap { m : Message -> m.channel }
        .flatMap<Message> { channel: MessageChannel -> channel.createMessage(printList()) }
        .subscribe()
}




