package com.github.emiliero.echelon.queue

import com.github.emiliero.echelon.model.values.QueueTimes
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object DateTimeCheck {
    fun printQueuePeriods(): String {
        var result = "_Queue opens 30 minutes before the listed start times_"

        QueueTimes.values().forEach { e ->
            val endTime = LocalTime.parse(e.toString()).plusMinutes(105)
            result += "\n> **${e.timeString}**: $e-$endTime"
        }

        return result
    }

    fun validateIfQueueTimeIsValid(): Boolean {
        val current = LocalDateTime.now()

        return checkIfCorrectDay(current)
    }

    private fun checkIfCorrectDay(currentDay: LocalDateTime): Boolean {
        val currentDayOfTheWeek = currentDay.dayOfWeek

        for (rehearsalDays in QueueTimes.values()) {
            if (rehearsalDays.timeString.toLowerCase() == currentDayOfTheWeek.toString().toLowerCase()) {
                return checkIfTimeOfDayIsCorrect(currentDay.toLocalTime(), rehearsalDays.timeString)
            }
        }
        return false
    }

    private fun checkIfTimeOfDayIsCorrect(currentTime: LocalTime, rehearsalDay: String): Boolean {
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val timeOfRehearsal: QueueTimes = QueueTimes.valueOf(rehearsalDay)
        val startOfQueueTime: LocalTime = LocalTime.parse(timeOfRehearsal.toString(), timeFormatter).minusMinutes(30) // Half an hour before the rehearsal starts
        val endOfQueueTime: LocalTime = LocalTime.parse(timeOfRehearsal.toString(), timeFormatter).plusMinutes(105) // When the rehearsal ends

        return currentTime.isAfter(startOfQueueTime) && currentTime.isBefore(endOfQueueTime)
    }
}
