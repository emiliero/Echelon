package com.github.emiliero.echelon.queue

import com.github.emiliero.echelon.model.StudAss
import com.github.emiliero.echelon.model.Student

object ListActionsArray : IListActions {
    private var studentList : MutableList<Student> = ArrayList<Student>()
    private var queCount : Int = 1


    override fun addPersonToQueue(username : String, discriminator : String) : String {
        val studentInQue = isStudentInQueue(
            username,
            discriminator
        )
        var message : String = ""

        if(!studentInQue){
            val p = Student(username,
                discriminator, queCount)
            if(studentList.any()){
                studentList.add(studentList.lastIndex, p)
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
        if (studentList.none()) {
            return "There's no one in the queue"
        }

        var student : Student = studentList[0]
        var result = "You are not in the queue, ${student.name}. If you want to join, use `!join`"

        studentList.removeIf { t: Student ->
            student=t
            queCount -=1
            result = "${student.name} has been removed from their spot in the queue, spot ${student.placeInQue}"
            t.name == username && t.id == discriminator
        }
        shiftList();

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
            if(!value.placeInQue.equals(index+1)){
                value.placeInQue = index+1;
            }

        }
        println(studentList);
    }


    override fun clearList(username: String, discriminator: String): String {
        var message ="";
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


}
