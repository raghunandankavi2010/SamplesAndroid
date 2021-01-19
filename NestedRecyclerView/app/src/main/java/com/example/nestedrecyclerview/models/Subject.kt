package com.example.nestedrecyclerview.models

import java.util.*

data class Subject(
    var id: Int = 0,
    var subjectName: String,
    var chapters: ArrayList<Chapter>
)