package com.indiainnovates.pucho.dagger.modules;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.indiainnovates.pucho.dagger.UserScope;
import com.indiainnovates.pucho.utils.UrlStrings;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import okio.Buffer;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

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
        final OkHttpClient client = new OkHttpClient();


        //InputStream caInput = application.getApplicationContext().getResources().openRawResource(R.raw.nginx);
        client.setSslSocketFactory(trustAllHosts().getSocketFactory());
        client.setHostnameVerifier(DO_NOT_VERIFY);
        client.setConnectTimeout(5, TimeUnit.SECONDS); // connect timeout
        client.setReadTimeout(5, TimeUnit.SECONDS);


        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request originalRequest = chain.request(); //Current Request
                //originalRequest.header()\
                //originalRequest.header("charset=utf-8");
                //RequestBody rb = RequestBody.create(originalRequest.body().contentType(),bodyToString(originalRequest));
                //originalRequest.newBuilder().post(rb);

                //if(originalRequest.body().toString()!=null)
                //Log.i("Body",bodyToString(originalRequest));
                Log.i("SSL Factory", "" + client.getSslSocketFactory());
                Log.i("Host Name", "" + client.getHostnameVerifier());
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
                /*BufferedSource buffer = Okio.buffer(response.body().source());
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
                        (String.format("Got response HTTP %s %s \n\n with body %s \n\n with headers %s ", response.code(), response.message(), response.body().string(), response.headers())));*/


                // in case you have zipped response
                //BufferedSource buffer = Okio.buffer(new GzipSource(response.body().source()));
                //String content = buffer.readUtf8();

                // remove header
                /*ResponseBody wrappedBody = ResponseBody.create(contentType, content);
                return response.newBuilder().removeHeader("Content-Encoding").body(wrappedBody).build();*/

                return response;
            }
        });
        return client;
    }

    @Provides
    @UserScope
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlStrings.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
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


    private static SSLContext trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
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
}
