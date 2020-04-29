package com.github.emiliero.echelon.commands

enum class QueueTimes(var timeString: String) {
    Tuesday("Tuesday"){
        override fun toString(): String {
            return "12:15"
        }
    },
    Wednesday("Wednesday"){
        override fun toString(): String {
            return "11:15"
        }
    },
    Thursday("Thursday"){
        override fun toString(): String {
            return "08:15"
        }
    },
    Friday("Friday"){
        override fun toString(): String {
            return "10:15"
        }
    }
}
