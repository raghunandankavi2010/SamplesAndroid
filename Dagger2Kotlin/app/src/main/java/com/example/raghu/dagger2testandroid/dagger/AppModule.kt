package com.example.raghu.dagger2testandroid.dagger

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.raghu.dagger2testandroid.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory

import java.io.IOException
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit

import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

import dagger.Module
import dagger.Provides

import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by raghu on 4/8/17.
 */

@Module
class AppModule {


    @Provides
    internal fun provideContext(application: Application): Context {
        return application
    }

    private val PREF_NAME = "prefs"


    @Provides
    @Singleton
    fun getAppPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }


    @Provides
    @Singleton
    internal fun provideOkHttpClient(application: Application): OkHttpClient {


        val interceptor = Interceptor { chain ->
            val originalRequest = chain.request() //Current Request

            var response = chain.proceed(originalRequest) //Get response of the request
            //final Response copy = response.newBuilder().removeHeader("Content-Type").build();


            //I am logging the response body in debug mode. When I do this I consume the response (OKHttp only lets you do this once) so i have re-build a new one using the cached body
            val bodyString = response.body()!!.string()
            //String newString = new String(bodyString.getBytes("UTF-8"), "UTF-8");
            //Log.i("Response Body in UTF 8", newString);
            Log.i("Request Body", " : " + bodyToString(originalRequest))

            Log.i("NetworkModule",java.lang.String.format("Sending request %s with headers %s ", originalRequest.url(), originalRequest.headers()))
            Log.i("", java.lang.String.format("Got response HTTP %s %s \n\n with body %s \n\n with headers %s ", response.code(), response.message(), bodyString, response.headers()))

            response = response.newBuilder().body(
                    ResponseBody.create(response.body()!!.contentType(), bodyString))
                    .build()



            response
        }

        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()


        return client
    }

   /* @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit
    }
*/
    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
        return retrofit
    }


    @Provides
    @Named("cached")
    @Singleton
    internal fun provideOkHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        val cache = Cache(application.cacheDir, cacheSize.toLong())
        return cache
    }

    companion object {

        private val DO_NOT_VERIFY = HostnameVerifier { hostname, session -> true }

        private fun bodyToString(request: Request): String {

            try {
                val copy = request.newBuilder().build()
                val buffer = Buffer()

                // make sure its not null to avoif NPE
                    copy?.body()?.writeTo(buffer)
                return buffer.readUtf8()
            } catch (e: IOException) {
                return "did not work"
            }

        }


        private fun trustAllHosts(): SSLContext? {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }


                override fun checkClientTrusted(chain: Array<X509Certificate>,
                                                authType: String)  {
                }


                override fun checkServerTrusted(chain: Array<X509Certificate>,
                                                authType: String) {
                }
            })

            // Install the all-trusting trust manager
            var sc: SSLContext? = null
            try {
                sc = SSLContext.getInstance("TLS")
                sc!!.init(null, trustAllCerts, java.security.SecureRandom())
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return sc
        }
    }

}
