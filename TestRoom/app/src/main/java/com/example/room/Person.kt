package com.example.room


import androidx.room.Embedded
import androidx.room.Relation


data class Person (
     @Embedded
     var personEntity: PersonEntity,
     @Relation(parentColumn = "personId", entityColumn = "personIdFk")
     var pets: List<Pet>)

