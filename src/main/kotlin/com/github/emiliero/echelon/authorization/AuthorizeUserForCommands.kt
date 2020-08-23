package com.github.emiliero.echelon.authorization

import com.github.emiliero.echelon.model.values.StudentAssistant

//TODO: Endre dette til å sjekke etter rolle
fun isUserAuthorizedForStudentAssistantCommands(username: String, discriminator: String): Boolean {
    for (enum in StudentAssistant.values()) {
        if (enum.studentAssistantUsername == username && enum.toString() == discriminator) {
            return true
        }
    }
    return false
}
