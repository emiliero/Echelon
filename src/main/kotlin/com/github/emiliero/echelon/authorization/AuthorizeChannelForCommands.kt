package com.github.emiliero.echelon.authorization

fun isUserAuthorizedForChannelCommand(channelId : String): Boolean {
    for (enum in Channels.values()) {
        if (enum.channelId == channelId) {
            return true
        }
    }
    return false
}