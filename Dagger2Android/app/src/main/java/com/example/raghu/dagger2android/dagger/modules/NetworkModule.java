package com.example.raghu.dagger2android.dagger.modules;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.raghu.dagger2android.Utils.Constants;
import com.example.raghu.dagger2android.dagger.UserScope;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


//import java.io.InputStream;
//import okio.GzipSource;

/**
 * Created by Raghunandan on 16-12-2015.
 */
@Module
public class NetworkModule {

    private final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };


    @Provides
    @UserScope
    OkHttpClient provideOkHttpClient(Application application) {


        /*
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        // From https://www.washington.edu/itconnect/security/ca/load-der.crt
        InputStream caInput = new BufferedInputStream(new FileInputStream("load-der.crt"));
        Certificate ca;
        try {
        ca = cf.generateCertificate(caInput);
        System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
        } finally {
        caInput.close();
        }

        // Create a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        // Create a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        // Create an SSLContext that uses our TrustManager
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tmf.getTrustManagers(), null);

         */
       // final OkHttpClient client = new OkHttpClient();


        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
        }};
        SSLSocketFactory sslSocketFactory = trustAllHosts().getSocketFactory();

        final OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .sslSocketFactory(sslSocketFactory,(X509TrustManager) trustAllCerts[0])
                .hostnameVerifier(DO_NOT_VERIFY)
                .connectTimeout(15,TimeUnit.SECONDS)
                .readTimeout(15,TimeUnit.SECONDS)
        .build();

        //client.hostnameVerifier().verify(DO_NOT_VERIFY,null);

        //InputStream caInput = application.getApplicationContext().getResources().openRawResource(R.raw.nginx);
        //client.setSslSocketFactory(trustAllHosts().getSocketFactory());
        //client.setHostnameVerifier(DO_NOT_VERIFY);
        //client.setConnectTimeout(5, TimeUnit.SECONDS); // connect timeout
        //client.setReadTimeout(5, TimeUnit.SECONDS);


        /*client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request originalRequest = chain.request(); //Current Request
                //originalRequest.header()\
                //originalRequest.header("charset=utf-8");
                //RequestBody rb = RequestBody.create(originalRequest.body().contentType(),bodyToString(originalRequest));
                //originalRequest.newBuilder().post(rb);

                //if(originalRequest.body().toString()!=null)
                //Log.i("Body",bodyToString(originalRequest));
                Log.i("SSL Factory", "" + client.socketFactory());
                Log.i("Host Name", "" + client.hostnameVerifier());
                Response response = chain.proceed(originalRequest); //Get response of the request
                //final Response copy = response.newBuilder().removeHeader("Content-Type").build();


                //I am logging the response body in debug mode. When I do this I consume the response (OKHttp only lets you do this once) so i have re-build a new one using the cached body
                String bodyString = response.body().string();
                //String newString = new String(bodyString.getBytes("UTF-8"), "UTF-8");
                //Log.i("Response Body in UTF 8", newString);
                Log.i("Request Body", " : " + bodyToString(originalRequest));

                Log.i("NetworkModule", String.format("Sending request %s with headers %s ", originalRequest.url(), originalRequest.headers()));
                Log.i("", (String.format("Got response HTTP %s %s \n\n with body %s \n\n with headers %s ", response.code(), response.message(), bodyString, response.headers())));

                response = response.newBuilder().body(
                        ResponseBody.create(response.body().contentType(), bodyString))
                        .build();

                // May be required to display multi language content
                *//*BufferedSource buffer = Okio.buffer(response.body().source());
                String content = buffer.readUtf8();

                response = response.newBuilder().body(
                        ResponseBody.create(response.body().contentType(), content))
                        .build();

                Log.i("Response Body", response.body().string());

                Log.i("NetworkModule",
                        String.format("Sending request %s with headers %s ",
                                originalRequest.url(),
                                originalRequest.headers()));
                Log.i("",
                        (String.format("Got response HTTP %s %s \n\n with body %s \n\n with headers %s ", response.code(), response.message(), response.body().string(), response.headers())));*//*


                // in case you have zipped response
                //BufferedSource buffer = Okio.buffer(new GzipSource(response.body().source()));
                //String content = buffer.readUtf8();

                // remove header
                *//*ResponseBody wrappedBody = ResponseBody.create(contentType, content);
                return response.newBuilder().removeHeader("Content-Encoding").body(wrappedBody).build();*//*

                return response;
            }
        });*/
        return client;
    }

    @Provides
    @UserScope
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    private static String bodyToString(final Request request) {

        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            if (copy != null && copy.body() != null) // make sure its not null to avoif NPE
                copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

// Old style
    private static SSLContext trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
        }};

        // Install the all-trusting trust manager
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sc;
    }


    @Provides
    @Named("cached")
    @UserScope
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @UserScope
    SharedPreferencesGet provideSharedPreferences(SharedPreferences sharedPreferences) {
        return new SharedPreferencesGet(sharedPreferences);
    }

   /* class LoggingInterceptor implements Interceptor {
        @Override public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();



            long t1 = System.nanoTime();
            Log.i("",String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            String bodyString = response.body().string();

            Log.i("Response body : ",bodyString);

           *//* Log.i("NetworkModule",
                    String.format("Sending request %s with headers %s ",
                            request.url(),
                            request.headers()));
            Log.i("",
                    (String.format("Got response HTTP %s %s \n\n with body %s \n\n with headers %s ", response.code(), response.message(), response.body().string(), response.headers())));*//**//**//*
            long t2 = System.nanoTime();
            Log.i("",String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }*/

    public static class LoggingInterceptor implements Interceptor {
        @Override public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            String requestLog = String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers());
            //YLog.d(String.format("Sending request %s on %s%n%s",
            //        request.url(), chain.connection(), request.headers()));
            if(request.method().compareToIgnoreCase("post")==0){
                requestLog ="\n"+requestLog+"\n"+bodyToString(request);
            }
            Log.d("TAG","request"+"\n"+requestLog);

            Response response = chain.proceed(request);
            long t2 = System.nanoTime();

            String responseLog = String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers());

            String bodyString = response.body().string();

            Log.d("TAG","response"+"\n"+responseLog+"\n"+bodyString);

            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), bodyString))
                    .build();
            //return response;
        }
    }


}
