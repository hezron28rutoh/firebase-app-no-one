package com.example.morningfirefoxdatabaseapp

class User {
    var userName:String = ""
    var userEmail:String = ""
    var userIdNumber:String = ""
    var id:String = ""

    constructor(name: String, eMail: String, idNumber: String, id: String) {
        this.userName = name
        this.userEmail = eMail
        this.userIdNumber = idNumber
        this.id = id
    }
    constructor()
}