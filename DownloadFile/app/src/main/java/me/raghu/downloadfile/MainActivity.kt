package me.raghu.downloadfile

import android.os.Bundle
import android.provider.Contacts
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import java.net.URL
import java.util.*
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity(), CoroutineScope {
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val downloadFile = DownloadFile


    private val uiScope = CoroutineScope(coroutineContext)

    private var value = 0
    private val list = ArrayList<String>()
    // private var downloadFile: DownloadFile = DownloadFile


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // huge image scaled down and displayed
        list.add("https://upload.wikimedia.org/wikipedia/commons/2/24/Willaerts_Adam_The_Embarkation_of_the_Elector_Palantine_Oil_Canvas-huge.jpg")
        // medium sized images
        list.add("https://78.media.tumblr.com/tumblr_m3drgiOSKp1r73wdao1_500.jpg")
        list.add("https://78.media.tumblr.com/Jjkybd3nSihj7pn4NtSSVDlho1_500.jpg")
        list.add("https://78.media.tumblr.com/tumblr_lokay4xhn01qij6yko1_500.jpg")
        list.add("https://78.media.tumblr.com/tumblr_lxr0e7mdvf1qa6z3eo1_500.jpg")

        // initially progress bar is hidden
        progressBar.visibility = View.GONE
        button.setOnClickListener {
            val channel = Channel<Float>()
            uiScope.launch {
                if(value < list.size) {
                    val url = URL(list[value])
                    progressBar.visibility = View.VISIBLE
                    val byteArray = downloadFile.downloadFile(url, channel)
                    val bitmap = downloadFile.processImage(
                        byteArray!!,
                        resources.getDimensionPixelSize(R.dimen.crop),
                        resources.getDimensionPixelSize(R.dimen.crop)
                    )
                    val d = RoundedDrawable(bitmap, resources.getDimensionPixelSize(R.dimen.crop).toFloat())
                    avatar.setImageDrawable(d)
                    value++
                }

            }
           uiScope.launch {
               for (y in channel) {
                   progressBar.progress = y
                   if(y == 100f) {
                       progressBar.visibility = View.GONE
                   }
               }
           }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
