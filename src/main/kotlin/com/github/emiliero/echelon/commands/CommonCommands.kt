package com.github.emiliero.echelon.commands

import com.github.emiliero.echelon.model.values.AdminCommands
import com.github.emiliero.echelon.model.values.CommonCommands
import com.github.emiliero.echelon.queue.ListActionsArray.addPersonToQueue
import com.github.emiliero.echelon.queue.ListActionsArray.checkPositionInQueue
import com.github.emiliero.echelon.queue.ListActionsArray.clearList
import com.github.emiliero.echelon.queue.ListActionsArray.moveNextStudentIntoChannel
import com.github.emiliero.echelon.queue.ListActionsArray.printList
import com.github.emiliero.echelon.queue.ListActionsArray.removePersonFromQueue
import com.github.emiliero.echelon.queue.ListActionsArray.reportSummary
import discord4j.core.DiscordClient
import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.MessageChannel
import discord4j.core.`object`.entity.User
import discord4j.core.event.domain.message.MessageCreateEvent

fun queueActions(client: DiscordClient) {
    joinQueue(client)
    leaveQueue(client)
    printQueue(client)
    clearQueue(client)
    addNextStudentInLineToChannel(client)
    checkPosition(client)
    printReport(client)
}

private fun joinQueue(client: DiscordClient) {
    var username = ""
    var discriminator = ""
    var id = ""

    client.eventDispatcher.on(MessageCreateEvent::class.java)
        .map { obj: MessageCreateEvent -> obj.message }
        .filter { message: Message ->
            message.author.map { user: User -> !user.isBot }.orElse(false)
        }
        .filter { message: Message ->
            message.content.orElse("").equals(CommonCommands.Join.commandString, ignoreCase = true)
        }.flatMap { m: Message ->
            username = m.author.get().username
            discriminator = m.author.get().discriminator
            id = m.author.get().id.toString().split("{", "}")[1]
            m.channel
        }
        .flatMap<Message> { channel: MessageChannel ->
            channel.createMessage(
                addPersonToQueue(
                    username,
                    discriminator,
                    id
                )
            )
        }
        .subscribe()
}

private fun leaveQueue(client: DiscordClient) {
    var username = ""
    var discriminator = ""

    client.eventDispatcher.on(MessageCreateEvent::class.java)
        .map { obj: MessageCreateEvent -> obj.message }
        .filter { message: Message ->
            message.author.map { user: User -> !user.isBot }.orElse(false)
        }
        .filter { message: Message ->
            message.content.orElse("").equals(CommonCommands.Leave.commandString, ignoreCase = true)
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
            message.content.orElse("").equals(CommonCommands.PrintQueue.commandString, ignoreCase = true)
        }.flatMap { m : Message -> m.channel }
        .flatMap<Message> { channel: MessageChannel -> channel.createMessage(printList()) }
        .subscribe()
}

private fun clearQueue(client : DiscordClient){
    var username =""
    var discriminator=""

    client.eventDispatcher.on(MessageCreateEvent::class.java)
        .map { obj:MessageCreateEvent -> obj.message}
        .filter{message: Message? ->
            message!!.author.map { user : User -> !user.isBot}.orElse(false)

        }
        .filter { message: Message ->
            message.content.orElse("").equals(AdminCommands.Clear.commandString, ignoreCase = true)
        }.flatMap {m:Message ->
            username = m.author.get().username
            discriminator = m.author.get().discriminator
            m.channel}
        .flatMap {channel: MessageChannel? ->  channel!!.createMessage(clearList(username, discriminator))}
        .subscribe()
}

private fun checkPosition(client : DiscordClient){
    var username = ""
    var discriminator=""
    var snowflake = ""

    client.eventDispatcher.on(MessageCreateEvent::class.java)
        .map { obj:MessageCreateEvent -> obj.message}
        .filter{message: Message? ->
            message!!.author.map { user : User -> !user.isBot}.orElse(false)
        }
        .filter { message: Message ->
            message.content.orElse("").equals(CommonCommands.Position.commandString, ignoreCase = true)
        }.flatMap {m:Message ->
            username = m.author.get().username
            discriminator = m.author.get().discriminator
            snowflake = m.author.get().id.toString() //Snowflake
            m.channel
        }
        .flatMap {channel: MessageChannel? ->  channel!!.createMessage(checkPositionInQueue(username, discriminator, snowflake))}
        .subscribe()
}

private fun addNextStudentInLineToChannel(client : DiscordClient){
    var studassUsername = ""
    var studassDiscriminator = ""
    var id = ""

    client.eventDispatcher.on(MessageCreateEvent::class.java)
        .map{obj:MessageCreateEvent -> obj.message}
        .filter{message:Message? ->
            message!!.author.map { user: User -> !user.isBot }.orElse(false)
        }
        .filter{message: Message ->
            message.content.orElse("").equals(AdminCommands.Next.commandString, ignoreCase = true)
        }.flatMap { m:Message ->
            studassUsername = m.author.get().username
            studassDiscriminator = m.author.get().discriminator
            id = m.author.get().id.toString() //Snowflake
            m.channel
        }
        .flatMap { channel: MessageChannel -> channel.createMessage(moveNextStudentIntoChannel(studassUsername, studassDiscriminator, id))}
        .subscribe()
}

private fun printReport(client : DiscordClient) {
    var studassUsername = ""
    var studassDiscriminator=""

    client.eventDispatcher.on(MessageCreateEvent::class.java)
        .map{obj:MessageCreateEvent -> obj.message}
        .filter{message:Message? ->
            message!!.author.map { user: User -> !user.isBot }.orElse(false)
        }
        .filter{message: Message ->
            message.content.orElse("").equals(AdminCommands.Report.commandString, ignoreCase = true)
        }.flatMap { m:Message ->

            studassUsername = m.author.get().username
            studassDiscriminator = m.author.get().discriminator
            m.channel
        }
        .flatMap { channel: MessageChannel -> channel.createMessage(reportSummary(channel.id.toString(),studassUsername, studassDiscriminator))}
        .subscribe()
}


