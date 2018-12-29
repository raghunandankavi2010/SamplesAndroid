@file:Suppress("CAST_NEVER_SUCCEEDS")

package raghu.me.myapplication.di

import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import raghu.me.myapplication.repo.ListRepositoryImpl


import raghu.me.myapplication.ui.ListScreenViewModel


val applicationModule = module {



    single<raghu.me.myapplication.di.ListRepository> { ListRepositoryImpl() }

    // ListScreenViewModel
    viewModel { ListScreenViewModel(get()) }

}
