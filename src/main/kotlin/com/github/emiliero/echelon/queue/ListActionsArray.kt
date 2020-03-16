package com.github.emiliero.echelon.queue

import com.github.emiliero.echelon.model.StudAss
import com.github.emiliero.echelon.model.Student
import discord4j.core.`object`.util.Snowflake

object ListActionsArray : IListActions {
    private var studentList : MutableList<Student> = ArrayList<Student>()
    private var queCount : Int = 0


    override fun addPersonToQueue(username : String, discriminator : String) : String {
        val studentInQue = isStudentInQueue(
            username,
            discriminator
        )
        var message : String = ""

        if(!studentInQue){
            val p = Student(username,
                discriminator, studentList.size+1)
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
        var usernameToBeRemoved =""
        var placeInQueToBeRemoved=0
        if(studentList.isNotEmpty()) {
            for ((index, value) in iterator.withIndex()) {
                if(value.name.equals(username)&&value.id.equals(discriminator)){
                    usernameToBeRemoved = value.name
                    placeInQueToBeRemoved = value.placeInQue
                    studentList.removeAt(index)
                }
            }

            result = "${usernameToBeRemoved} has been removed from their spot in the queue, spot ${placeInQueToBeRemoved}"
            shiftList();
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
            builder.append("list is empty")
        }

        return builder.toString()
    }

    override fun shiftList() {
        val iterator = studentList.iterator()
        for ((index, value) in iterator.withIndex()) {
            if (!value.placeInQue.equals(index + 1)) {
                value.placeInQue = index + 1;
            }

        }

    }


    override fun clearList(username: String, discriminator: String): String {
        var message =""
        queCount = 0
        val iterator = listOf(
            StudAss("larseknu", "8231"),
            StudAss("Anders", "7082"),
            StudAss("Zoryi", "9995"),
            StudAss("Joandreas", "9781"),
            StudAss("Mette", "7398")
        )
        iterator.forEach { studass ->
            if(studass.name.equals(username) && studass.id.equals(discriminator)){
                studentList.clear();
            }
        }
        if(studentList.isEmpty()){
            message="Queue cleared"
        }else{
            message="You do not have permissions to do that action"
        }

        return message;
    }

    private fun isStudentInQueue(username: String, discriminator: String) : Boolean{
        val iterator = studentList.iterator()
        var isInQue = false

        iterator.forEach { student ->
            if(student.name == username && student.id == discriminator){
                isInQue = true
            }
        }
        return isInQue
    }

     fun moveNextStudentIntoChannel(studassUsername : String, id : String):String{
         var id = id.split("{", "}")[1]
         shiftList()
         var message = ""
         if(studentList.isNotEmpty()) {
             var user: Student = studentList.get(0);
             message = "<@${id}> is the next one up with ${studassUsername}"
             studentList.removeAt(0)
             queCount-=1

         }
        return message
    }


    fun checkPositionInQueue(username:String, id:String) : String {
        var message = "Could not find your place in the list"
        var position = 0;
        val iterator = studentList.iterator()
        for ((index, value) in iterator.withIndex()) {
           if(value.name.equals(username) && value.id.equals(id)){
               position = value.placeInQue

               message = "${username} is at position ${position}"
           }

        }
        return message
    }


}
