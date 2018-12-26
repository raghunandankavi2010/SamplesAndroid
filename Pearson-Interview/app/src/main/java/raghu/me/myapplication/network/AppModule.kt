@file:Suppress("CAST_NEVER_SUCCEEDS")

package raghu.me.myapplication.network

import org.koin.dsl.module.module


val applicationModule = module {

    single<RetrofitInterface> {
        RetrofitDependency()
    }

}
