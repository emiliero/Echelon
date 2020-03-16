package com.github.emiliero.echelon.queue

import com.github.emiliero.echelon.model.StudAss
import com.github.emiliero.echelon.model.Student

object ListActionsArray : IListActions {
    private var studentList : MutableList<Student> = ArrayList<Student>()
    private var queCount : Int = 0
    private val listOfStudentAssistants : List<StudAss> = listOf<StudAss>(
        StudAss("larseknu", "8231"),
        StudAss("Anders", "7082"),
        StudAss("Zoryi", "9995"),
        StudAss("Joandreas", "9781"),
        StudAss("Mette", "7398"),
        StudAss("Yearn", "3599")
    )


    override fun addPersonToQueue(username : String, discriminator : String, userID : String) : String {
        val studentInQue = isStudentInQueue(
            username,
            discriminator
        )
        var message : String = ""

        if(!studentInQue){
            val p = Student(username, discriminator, userID,studentList.size+1)
            if(studentList.any()){
                studentList.add(studentList.lastIndex+1, p)
                message = "${p.name} has been added to the queue you are at spot ${p.placeInQue}"
                queCount +=1

            } else {
                studentList.add(p)
                message ="${p.name} has been added to the queue, you are the first one up at place ${p.placeInQue}"
                queCount +=1
            }
        } else {
            message = "${username} is already in the queue, you can't join twice <:birthdayPepega:688469221499207744>"
        }

        return message
    }

    override fun removePersonFromQueue(username: String, discriminator: String) : String {
        val iterator = studentList.iterator()
        if (studentList.none()) {
            return "There's no one in the queue"
        }

        var result = "You are not in the queue, ${username}. If you want to join, use `!join`"
        var usernameToBeRemoved ="" //TODO endre til ID
        var placeInQueToBeRemoved=0

        if(studentList.isNotEmpty()) {
            for ((index, value) in iterator.withIndex()) {
                if(value.name == username && value.discordId == discriminator){
                    usernameToBeRemoved = value.name
                    placeInQueToBeRemoved = value.placeInQue
                    studentList.removeAt(index)
                    shiftList()
                }
            }

            result = "$usernameToBeRemoved has been removed from their spot in the queue, spot $placeInQueToBeRemoved"
        }

        return result
    }

    override fun printList(): String{
        var builder : StringBuilder = StringBuilder()
        var iterator = studentList.iterator()
        if(studentList.isNotEmpty()) {
            iterator.forEach { student ->
                builder.append(student.placeInQue.toString() + ": " + student.name + "\n")
            }
        }else{
            builder.append("The list is empty")
        }

        return builder.toString()
    }

    override fun shiftList() {
        val iterator = studentList.iterator()
        for ((index, value) in iterator.withIndex()) {
            if (value.placeInQue != index + 1) {
                value.placeInQue = index + 1
            }
        }
    }


    override fun clearList(username: String, discriminator: String): String {
        var message =""
        queCount = 0

        for (enum in StudentAssistant.values()) {
            if (enum.name == username && discriminator == enum.toString()) {
                studentList.clear()
            } else {
                return "You do not have permission to clear the list"
            }
        }
        /*val iterator = listOfStudentAssistants //StudentAssistant.values()
        iterator.forEach { studentAssistant ->
            if(studentAssistant.name == username && studentAssistant.toString() == discriminator){
                studentList.clear();
            } else {
                return "You do not have permission to clear the list"
            }
        }*/

        message = if (studentList.isEmpty()){
            "Queue cleared"
        } else {
            "You do not have permissions to do that action"
        }

        return message
    }

    private fun isStudentInQueue(username: String, discriminator: String) : Boolean {
        val iterator = studentList.iterator()
        var isInQue = false

        iterator.forEach { student ->
            if(student.name == username && student.discordId == discriminator){
                isInQue = true
            }
        }
        return isInQue
    }

    fun moveNextStudentIntoChannel(id : String): String {
        //TODO: Sette restriction på rolle
        val studassId = id.split("{", "}")[1]

        if(studentList.isNotEmpty()) {
            val user: Student = studentList[0];
            studentList.removeAt(0)
            queCount-=1
            shiftList()
            return "<@${user.snowflake}> is the next one up with <@${studassId}>"
        }
        return "<@${studassId}>, there are no students in queue"
    }

    fun checkPositionInQueue(username : String, discriminator: String) : String{
        var message = "You are not in the queue at the moment. Use !join"
        var position=0

        val iterator = studentList.iterator()
        iterator.forEach {student ->
            if (student.name == username && student.discordId == discriminator) {
                position = student.placeInQue
                message = "${username} is at position ${position} in the queue"
            }
        }
        return message
    }
}
