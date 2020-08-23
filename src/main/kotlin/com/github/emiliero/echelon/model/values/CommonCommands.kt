package com.github.emiliero.echelon.model.values

enum class CommonCommands(var commandString: String) {
    Help("!help"){
        override fun toString(): String {
            return "Display commands"
        }
    },
    Join("!join"){
        override fun toString(): String {
            return "Join the queue to get help from a student assistant"
        }
    },
    Leave("!leave"){
        override fun toString(): String {
            return "Leave the queue. Use this if you don't need help anymore"
        }
    },
    PrintQueue("!print"){
        override fun toString(): String {
            return "Print the queue"
        }
    },
    Position("!position"){
        override fun toString(): String {
            return "Check your position in the queue"
        }
    }
}
