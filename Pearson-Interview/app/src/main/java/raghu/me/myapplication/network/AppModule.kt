package raghu.me.myapplication.network

import org.koin.dsl.module.module


val applicationModule = module {

    single {
        RetrofitDependency()
    }

}
