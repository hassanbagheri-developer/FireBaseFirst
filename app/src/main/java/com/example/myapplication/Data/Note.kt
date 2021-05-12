package com.example.myapplication.Data

data class Note(
    val date: String = "",
    val titel: String = "",
    val desc: String = ""
) {
    constructor(date: String, titel: String, desc: String ,  id :String) : this(date, titel, desc)
}
