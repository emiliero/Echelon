package com.github.emiliero.echelon.commands

enum class QueueTimes(var timeString: String) {
    Monday("Monday"){
        override fun toString(): String {
            return "14:15"
        }
    }/*,
    Wednesday("Wednesday"){
        override fun toString(): String {
            return "14:45"
        }
    },
    Thursday("Thursday"){
        override fun toString(): String {
            return "08:15"
        }
    },
    Friday("Friday"){
        override fun toString(): String {
            return "14:15"
        }
    }*/
}
