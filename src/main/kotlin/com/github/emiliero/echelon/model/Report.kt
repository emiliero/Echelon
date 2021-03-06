package com.github.emiliero.echelon.model

import com.github.emiliero.echelon.queue.DateTimeCheck
import java.time.LocalDateTime

class Report {
    private var queuers: Int = 0
    private val help = HashMap<String, ArrayList<Student>>()
    private var reportDateTime = LocalDateTime.now()

    fun addQueuedStudentToReport() {
        clearReportIfDateIsNotToday()
        if (queuers == 0) {
            queuers = 1
        } else {
            queuers++
        }
    }

    fun addHelpToReport(studAssId: String, studentGettingHelp: Student) {
        val students: ArrayList<Student>? = help[studAssId]

        if (students == null) {
            val tempList = ArrayList<Student>()
            tempList.add(studentGettingHelp)
            help[studAssId] = tempList
        } else {
            students.add(studentGettingHelp)
            help[studAssId] = students
        }
    }

    private fun clearReportIfDateIsNotToday() {
        if (!DateTimeCheck.checkIfDateIsOutsideOfQueueTime()) {
            reportDateTime = LocalDateTime.now()
            help.clear()
            queuers = 0
        }
    }

    override fun toString(): String {
        return if (help.isEmpty() && queuers == 0) {
            "Nothing to report today"
        } else {
            "**Report for day ${reportDateTime.dayOfWeek.toString().toLowerCase()}:** \n" +
                    "${printHelpValues()} \n" +
                    "Students in queue: $queuers"
        }
    }

    private fun printHelpValues(): String {
        var result = ""
        var studentsHelped = 0

        for (el in help) {
            result += "> <@${el.key}>: ${el.value.size}\n"
            studentsHelped += el.value.size

            for (student in el.value) {
                result += if (el.value.indexOf(student) == 0) {
                    "> - $student"
                } else {
                    ", $student"
                }
            }
            result += "\n"
        }
        result += "Students helped: $studentsHelped"

        return result
    }
}
