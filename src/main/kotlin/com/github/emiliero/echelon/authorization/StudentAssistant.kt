package com.github.emiliero.echelon.authorization

enum class StudentAssistant(var studentAssistantUsername: String) {
    Mats("AdmiralAnanas"){
        override fun toString(): String {
            return "8231"
        }
    },
    LarsErik("Lars-Erik"){
        override fun toString(): String {
            return "6401"
        }
    },
    Emilie("Emilie"){
        override fun toString(): String {
            return "0001"
        }
    },
    Soppen("Soppen"){
        override fun toString(): String {
            return "1001"
        }
    },
    DXCT("DXCT"){
        override fun toString(): String {
            return "7504"
        }
    }
}
