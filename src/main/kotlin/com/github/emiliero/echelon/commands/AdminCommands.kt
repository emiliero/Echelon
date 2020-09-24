package com.github.emiliero.echelon.commands

enum class AdminCommands(var commandString: String) {
    Next("!next"){
        override fun toString(): String {
            return "Get the next student in queue"
        }
    },
    Clear("!clear"){
        override fun toString(): String {
            return "Clear the entire list"
        }
    },
    Report("!report"){
        override fun toString(): String {
            return "Print report for the day"
        }
    }/*,
    Complete("!complete"){
        override fun toString(): String {
            return "Finished helping student"
        }
    },
    MakeStudass("!makestudass"){
        override fun toString(): String {
            return "Promote member to student assistant role"
        }
    }*/
}