package com.github.emiliero.echelon.queue

import com.github.emiliero.echelon.model.StudAss

interface IListActions {
    fun addPersonToQueue(username: String, discriminator: String): String
    fun removePersonFromQueue(username: String, discriminator: String): String
    fun printList(): String
    fun shiftList() : Unit
    fun clearList(username: String, discriminator: String) : String
}