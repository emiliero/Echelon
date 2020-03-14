package com.github.emiliero.echelon.commands

import com.github.emiliero.echelon.commands.list.listActions.addPersonToQueue
import com.github.emiliero.echelon.commands.list.listActions.printList
import com.github.emiliero.echelon.commands.list.listActions.removePersonFromQue
import com.github.emiliero.echelon.model.Person
import discord4j.core.DiscordClient
import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.MessageChannel
import discord4j.core.`object`.entity.User
import discord4j.core.event.domain.message.MessageCreateEvent

fun Join(client: DiscordClient) {
    joinQueue(client)
    leaveQueue(client)
    printQueue(client)
}

private fun leaveQueue(client: DiscordClient) {
    var username : String =""
    var discriminator : String =""
    client.eventDispatcher.on(MessageCreateEvent::class.java)
        .map { obj: MessageCreateEvent -> obj.message }
        .filter { message: Message ->
            message.author.map { user: User -> !user.isBot }.orElse(false)
        }
        .filter { message: Message ->
            message.content.orElse("").contains("!leave", ignoreCase = true)
        }
        .flatMap { m : Message ->
            username = m.author.get().username
            discriminator = m.author.get().discriminator
            m.channel
        }
        .flatMap<Message> { channel: MessageChannel -> channel.createMessage(removePersonFromQue(username, discriminator))}
        .subscribe()
}

private fun joinQueue(client: DiscordClient) {
    var username: String = "";
    var discriminator: String = "";
    client.eventDispatcher.on(MessageCreateEvent::class.java)
        .map { obj: MessageCreateEvent -> obj.message }
        .filter { message: Message ->
            message.author.map { user: User -> !user.isBot }.orElse(false)
        }
        .filter { message: Message ->
            message.content.orElse("").contains("!join", ignoreCase = true)
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

    private fun printQueue(client: DiscordClient){
        client.eventDispatcher.on(MessageCreateEvent::class.java)
            .map { obj: MessageCreateEvent -> obj.message }
            .filter { message: Message ->
                message.author.map { user: User -> !user.isBot}.orElse(false)
            }
            .filter { message: Message ->
                message.content.orElse("").contains("!printQueue", ignoreCase = true)
            }.flatMap { m : Message -> m.channel }
            .flatMap<Message> { channel: MessageChannel -> channel.createMessage(printList()) }
            .subscribe()
        }




