package com.github.emiliero.echelon

import com.github.emiliero.echelon.authorization.login
import com.github.emiliero.echelon.commands.printHelp
import com.github.emiliero.echelon.commands.queueActions
import discord4j.core.DiscordClient
import discord4j.core.DiscordClientBuilder
import discord4j.core.`object`.presence.Activity
import discord4j.core.`object`.presence.Presence

fun main(args: Array<String>) {
    val client: DiscordClient = DiscordClientBuilder(BuildConfig.TOKEN_KEY)
        .setInitialPresence(Presence.online(Activity.listening("!help")))
        .build()

    login(client)
    printHelp(client) //Help command
    queueActions(client) //Queue commands
    client.login().block()
}

