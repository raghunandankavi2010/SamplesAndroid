package com.example.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "person")
data class PersonEntity(@PrimaryKey  var personId: String,var personName: String)

