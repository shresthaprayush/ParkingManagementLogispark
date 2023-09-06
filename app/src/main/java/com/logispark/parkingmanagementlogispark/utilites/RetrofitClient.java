package com.logispark.parkingmanagementlogispark.utilites;

import android.content.Context;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//This class is class to handle all the retrofit working and creating notification client.
public class RetrofitClient {

    //Creating a final instance of retrofit.
    private static final RetrofitClient mInstance = new RetrofitClient();
    //Initializing the baseurl this changes in accordance to the server

    private String onlineserver;


    private Retrofit retrofit;

    //Initialing the cache, pragma and mcache to store data when offline as well
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String HEADER_PRAGMA = "Pragma";
    public Cache mCache;


    //A simple constructor of retrofit client
    public static
    RetrofitClient getmInstance() {
        return mInstance;
    }

    //An empty constructor
    private RetrofitClient() {
    }


    //This is used to provide offline cache storage features.
    public API getretrofit(Context context) {

        onlineserver = SharedPreferenceManager.getmInstance(context).geturl();

        String finalurl = "https://logisparktech.com/nepvent-parking/api/";
//        String finalurl = "http://192.168.1.122:8000/api/";



        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .addInterceptor(provideOfflineCacheInterceptor(context))
                .addNetworkInterceptor(provideCacheInterceptor(context))
                .addInterceptor(loggingInterceptor)
                .cache(provideCache(context));


        return new Retrofit.Builder().baseUrl(finalurl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build().create(API.class);
    }

    //This is used to set the value of cache and also allocate its size.

    private Cache provideCache(Context context) {
        if (mCache == null) {
            try {
                mCache = new Cache(new File(context.getCacheDir(), "http-cache"),
                        10 * 1024 * 1024); // 10 MB
            } catch (Exception e) {
                Log.e("sdf", "Could not create Cache!");

            }
        }
        return mCache;
    }

    //This is also used to validate offline data request and also give time period to retain cache.
    private Interceptor provideCacheInterceptor(final Context context) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                CacheControl cacheControl;

                if (RetrofitClient.this.isConnected(context)) {
                    cacheControl = new CacheControl.Builder()
                            .maxAge(0, TimeUnit.SECONDS)
                            .build();
                } else {
                    cacheControl = new CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build();
                }

                return response.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                        .build();

            }
        };
    }


    //This is also used to validate offline data request and also give time period to retain cache.
    private Interceptor provideOfflineCacheInterceptor(final Context context) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                if (!RetrofitClient.this.isConnected(context)) {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build();

                    request = request.newBuilder()
                            .removeHeader(HEADER_PRAGMA)
                            .removeHeader(HEADER_CACHE_CONTROL)
                            .cacheControl(cacheControl)
                            .build();
                }

                return chain.proceed(request);
            }
        };
    }


    //Checks if the network is present or not.
    public boolean isConnected(Context context) {
        try {
            android.net.ConnectivityManager e = (android.net.ConnectivityManager) context.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = e.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        } catch (Exception e) {
            Log.w("Tag", e.toString());
        }

        return false;
    }


}
