package com.github.emiliero.echelon.model.values

enum class StudentAssistant(var studentAssistantUsername: String) {
    //TODO: Change to a specific role instead of people (!makestudass command)
    LarsEmil("larseknu"){
        override fun toString(): String {
            return "8231"
        }
    },
    Anders("Anders"){
        override fun toString(): String {
            return "7082"
        }
    },
    Emilie("Zoryi"){
        override fun toString(): String {
            return "9995"
        }
    },
    Jo("Joandreas"){
        override fun toString(): String {
            return "9781"
        }
    },
    Mette("Mette"){
        override fun toString(): String {
            return "7398"
        }
    },
    JornHe("Yearn"){
        override fun toString(): String {
            return "3599"
        }
    }
}
