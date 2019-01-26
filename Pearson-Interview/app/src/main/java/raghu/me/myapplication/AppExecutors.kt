package raghu.me.myapplication

import android.os.Handler
import android.os.Looper
import raghu.me.myapplication.di.AppExecutorsInterface
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors(
    private val diskIO: Executor,
    private val networkIO: Executor,
    private val mainThread: Executor
): AppExecutorsInterface {

    constructor() : this(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(3),
        MainThreadExecutor()
    )

    override
    fun diskIO(): Executor {
        return diskIO
    }

    override
    fun networkIO(): Executor {
        return networkIO
    }

    override
    fun mainThread(): Executor {
        return mainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}