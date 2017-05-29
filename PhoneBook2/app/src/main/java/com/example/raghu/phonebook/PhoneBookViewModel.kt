package com.example.raghu.phonebook

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.example.raghu.phonebook.db.DatabaseCreator
import com.example.raghu.phonebook.entity.ContactEntity

/**
 * Created by raghu on 20/5/17.
 */

class PhoneBookViewModel(application: Application) : AndroidViewModel(application) {

    private val mObservableContacts: LiveData<List<ContactEntity>>
    init {

        ABSENT.setValue(null)

    }



    init {
        val databaseCreator = DatabaseCreator.getInstance(this.getApplication<Application>())

        val databaseCreated = databaseCreator?.isDatabaseCreated
        mObservableContacts = Transformations.switchMap(databaseCreated
        ) { isDbCreated ->
            if (java.lang.Boolean.TRUE != isDbCreated) { // Not needed here, but watch out for null

                ABSENT
            } else {

                databaseCreator?.database?.contactDao()?.loadContacts()
            }
        }

        databaseCreator?.createDb(this.getApplication<Application>())
    }
    companion object {

        private val ABSENT = MutableLiveData<List<ContactEntity>>()
    }

    fun getContacts(): LiveData<List<ContactEntity>> {
        return mObservableContacts
    }
}
