package com.github.emiliero.echelon.authorization

import com.github.emiliero.echelon.model.values.Channels

fun isUserAuthorizedForChannelCommand(channelId : String): Boolean {
    for (enum in Channels.values()) {
        if (enum.channelId == channelId) {
            return true
        }
    }
    return false
}
