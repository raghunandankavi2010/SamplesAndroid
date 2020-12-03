package com.example.room

import androidx.lifecycle.LiveData
import androidx.room.*



@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(personEntity: PersonEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pets: Pet)


    suspend fun insertBoth(person: Person) {
       insert(personEntity = person.personEntity)
        for (pet in person.pets) {
            pet.personIdFk = person.personEntity.personId
            insert(pet)
        }
    }

    @Transaction
    @Query("SELECT * FROM person")
    fun getAll(): LiveData<Person>
}


