package com.github.emiliero.echelon

import com.github.emiliero.dicey.BuildConfig
import discord4j.core.DiscordClient
import discord4j.core.DiscordClientBuilder
import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.MessageChannel
import discord4j.core.`object`.entity.User
import discord4j.core.event.domain.lifecycle.ReadyEvent
import discord4j.core.event.domain.message.MessageCreateEvent


class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val client: DiscordClient = DiscordClientBuilder(BuildConfig.TOKEN_KEY).build()

            login(client)

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

            client.login().block()
        }

        private fun login(client: DiscordClient) {
            client.eventDispatcher
                .on(MessageCreateEvent::class.java)
                .subscribe { event: MessageCreateEvent ->
                    event.message.content.ifPresent { c: String? -> println(c) }
                }

            client.eventDispatcher.on(ReadyEvent::class.java)
                .subscribe { event: ReadyEvent ->
                    val self = event.self
                    println(java.lang.String.format("Logged in as %s#%s", self.username, self.discriminator))
                }
        }
    }
}