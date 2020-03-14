package com.github.emiliero.echelon

import com.github.emiliero.echelon.login.login
import com.github.emiliero.echelon.commands.Join
import discord4j.core.DiscordClient
import discord4j.core.DiscordClientBuilder


fun main(args: Array<String>) {
    val client: DiscordClient = DiscordClientBuilder(BuildConfig.TOKEN_KEY).build()

    login(client)

    Join(client)

    client.login().block()
}

