package com.github.emiliero.echelon.commands.list

import com.github.emiliero.echelon.model.Student
import java.lang.StringBuilder
import kotlin.collections.ArrayList

object listActions {
    var studentList : MutableList<Student> = ArrayList<Student>()
    var queCount = 1


    fun addPersonToQueue(uName : String, discriminator : String) : String {
        val studentInQue = checkIfStudentIsInQueue(uName, discriminator)
        var message : String = ""

        if(!studentInQue){
            val p : Student = Student(uName, queCount, discriminator)
            if(studentList.any()){
                studentList.add(studentList.lastIndex, p)
                message = "${p.name} has been added to the queue you are at spot ${p.placeInQue}"
                queCount+=1
            } else {
                studentList.add(p)
                message ="${p.name} has been added to the queue, you are the first one up at place ${p.placeInQue}"
                queCount+=1
            }
        } else {
            message = "${uName} is already in the queue, you can't join twice <:birthdayPepega:688469221499207744>"
        }

        return message
    }

    fun removePersonFromQue(uName:String, discriminator: String) : String {
        var student : Student = studentList.get(0)

        studentList.removeIf { t: Student ->
            student=t
            queCount-=1
            t.name == uName && t.id == discriminator
        }

        return "${student.name} has been removed from their spot in the queue, spot ${student.placeInQue}"
    }

    private fun checkIfStudentIsInQueue(uName: String, discriminator: String) : Boolean{
        val iterator = studentList.iterator()
        var isInQue = false

        iterator.forEach { student ->
           if(student.name == uName && student.id == discriminator){
               isInQue = true
           }
        }
        return isInQue
    }


    fun printList(): String{
        var builder : StringBuilder = StringBuilder()
        var iterator = studentList.iterator()

        iterator.forEach { student ->
            builder.append(student.placeInQue.toString() + ": " + student.name +"\n")
        }
        return builder.toString()
    }
}
