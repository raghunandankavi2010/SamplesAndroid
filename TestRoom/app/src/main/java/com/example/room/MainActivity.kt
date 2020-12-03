package com.example.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.example.room.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var liveData: LiveData<Person>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.button.setOnClickListener {
               add()
        }

        binding.button2.setOnClickListener {
            show()
        }
    }

    private fun show() {
        liveData = DatabaseClient.getInstance(applicationContext)?.let {
            it.appDatabase.personDao().getAll()
        }!!
        liveData.observe(this, { person ->

            Log.i(
                "MainActivity",
                "${person.personEntity.personName} and pets are ${person.pets[0].name} and ${
                    person.pets[1].name
                }"
            )
        })
    }


    private fun add() {
        val personEntity = PersonEntity(personId = "10","A")

        val list = mutableListOf<Pet>()

        val pet1 = Pet("10","Dobberman","1")
        list.add(pet1)

        val pet2 = Pet("11","Pomerian","1")
        list.add(pet2)

        val person = Person(personEntity, list)

        lifecycleScope.launch {
            DatabaseClient.getInstance(applicationContext)?.let {
                it.appDatabase.personDao().insertBoth(person)
            }
        }
    }
}