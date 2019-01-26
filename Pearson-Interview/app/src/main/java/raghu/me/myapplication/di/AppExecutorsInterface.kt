package raghu.me.myapplication.di

import java.util.concurrent.Executor

interface AppExecutorsInterface {

    fun diskIO(): Executor

    fun networkIO(): Executor

    fun mainThread(): Executor

}