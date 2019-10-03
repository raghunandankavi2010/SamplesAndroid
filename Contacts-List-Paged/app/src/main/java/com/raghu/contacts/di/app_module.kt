package com.raghu.contacts.di

import com.raghu.contacts.ui.contact.ContactsViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext


val groupModule: Module = applicationContext {

    viewModel { ContactsViewModel(androidApplication().contentResolver) }
}