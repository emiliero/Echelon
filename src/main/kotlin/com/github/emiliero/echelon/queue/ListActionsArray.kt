package com.github.emiliero.echelon.queue

import com.github.emiliero.echelon.authorization.isUserAuthorizedForStudentAssistantCommands
import com.github.emiliero.echelon.model.Report
import com.github.emiliero.echelon.model.Student

object ListActionsArray : IListActions {
    private var studentList : MutableList<Student> = ArrayList<Student>()
    private var report = Report()

    override fun addPersonToQueue(username : String, discriminator : String, userID : String) : String {
        if (!DateTimeCheck.checkIfDateIsOutsideOfQueueTime()) {
            return "<@${userID}> you can't join the queue at this time. " +
                    "Join in some of the following periods: \n" +
                    DateTimeCheck.printQueuePeriods()
        }

        val studentInQue = isStudentInQueue(username, discriminator)
        val p = Student(username, discriminator, userID, studentList.size+1)

        return if(!studentInQue){
            if(studentList.any()){
                studentList.add(studentList.lastIndex+1, p)
                report.addQueuedStudentToReport()
                "<@${p.snowflake}> has been added to the queue you are at spot ${p.placeInQue}"
            } else {
                studentList.add(p)
                report.addQueuedStudentToReport()
                "<@${p.snowflake}> has been added to the queue, you are the first one up at place ${p.placeInQue}"
            }
        } else {
            "<@${p.snowflake}> is already in the queue, you can't join twice <:pepega:687979040019054803>"
        }
    }

    override fun removePersonFromQueue(username: String, discriminator: String) : String {
        if (studentList.none()) {
            return "There's no one in the queue"
        }

        var result = "You are not in the queue, ${username}. If you want to join, use `!join`"
        var snowFlakeToBeRemoved = ""
        var placeInQueToBeRemoved=0

        if(studentList.isNotEmpty()) {
            run {
                val iterator: MutableIterator<Student> = studentList.iterator()
                while (iterator.hasNext()) {
                    val value = iterator.next()
                    if (value.name == username && value.discordId == discriminator) {
                        placeInQueToBeRemoved = value.placeInQue
                        snowFlakeToBeRemoved= value.snowflake
                        iterator.remove()
                    }
                }
            }
            if(snowFlakeToBeRemoved.isNotEmpty()){
                shiftList()
                result = "<@$snowFlakeToBeRemoved>"+
                        " has been removed from their spot in the queue, spot $placeInQueToBeRemoved"
            }
        }

        return result
    }

    override fun clearList(username: String, discriminator: String): String {
        return if (isUserAuthorizedForStudentAssistantCommands(username, discriminator)) {
            if (studentList.isNotEmpty()) {
                studentList.clear()
                "Queue is cleared"
            } else {
                "The queue was already empty"
            }
        } else {
            "You are not authorized for this command"
        }
    }

    private fun isStudentInQueue(username: String, discriminator: String) : Boolean {
        val iterator = studentList.iterator()
        var isInQue = false

        iterator.forEach { student ->
            if(student.name == username && student.discordId == discriminator) {
                isInQue = true
            }
        }
        return isInQue
    }

    fun moveNextStudentIntoChannel(username: String, discriminator: String, id : String): String {
        val studentAssistantId = createValidSnowFlake(id)

        return if(isUserAuthorizedForStudentAssistantCommands(username, discriminator)) {
            val user: Student = studentList.removeAt(0)
            report.addHelpToReport(studentAssistantId, user)
            shiftList()
            return "<@${user.snowflake}> is the next one up with <@${studentAssistantId}> <a:pepeHack:753229418498883624>"
        } else if (!studentList.isNullOrEmpty()) {
            "<@${studentAssistantId}> - you are not authorized for this command."
        } else {
            "<@${studentAssistantId}>, there are no students in queue."
        }
    }

    fun checkPositionInQueue(username: String, discriminator: String, snowflake: String) : String {
        val authorSnowflake: String = snowflake.split("{", "}")[1]
        var message = "<@${authorSnowflake}> is not in the queue at the moment. Use `!join`"
        val iterator = studentList.iterator()

        iterator.forEach {student ->
            if (student.name == username && student.discordId == discriminator) {
                val position = student.placeInQue
                message = "<@${student.snowflake}>" +
                        " is at position $position in the queue"
            }
        }
        return message
    }

    private fun shiftList() {
        val iterator = studentList.iterator()

        for ((index, value) in iterator.withIndex()) {
            if (value.placeInQue != index + 1) {
                value.placeInQue = index + 1
            }
        }
    }

    override fun printList(): String{
        val builder : StringBuilder = StringBuilder()
        val iterator = studentList.iterator()

        // TODO: @ person som kaller på metoden (bruker kommandoen)
        if(studentList.isNotEmpty()) {
            iterator.forEach { student ->
                builder.append(student.placeInQue.toString() + ": " + student.name + "\n")
            }
        } else {
            builder.append("The list is empty")
        }

        return builder.toString()
    }

    fun reportSummary(username : String, discriminator : String): String {
        return if(isUserAuthorizedForStudentAssistantCommands(username, discriminator)) {
            report.toString()
        } else {
            "This command is restricted to another channel or user group"
        }
    }

    fun createValidSnowFlake(id : String) : String {
        return id.split("{", "}")[1]
    }
}
