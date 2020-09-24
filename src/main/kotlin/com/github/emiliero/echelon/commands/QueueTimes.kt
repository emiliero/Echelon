package com.github.emiliero.echelon.commands

enum class QueueTimes(var timeString: String) {
    Monday("Monday"){
        override fun toString(): String {
            return "14:15"
        }
    }
}
