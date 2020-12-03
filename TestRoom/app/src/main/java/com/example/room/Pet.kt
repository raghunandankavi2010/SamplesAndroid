package com.example.room


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey


@Entity(
    foreignKeys = [ForeignKey(
        entity = PersonEntity::class,
        parentColumns = arrayOf("personId"),
        childColumns = arrayOf("personIdFk"),
        onDelete = CASCADE
    )]
)
data class Pet(
    @PrimaryKey
    var petId: String,
    var name: String,
    var personIdFk: String
)