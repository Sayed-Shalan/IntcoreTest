package com.sayed.intcoretest.app;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.sayed.intcoretest.BuildConfig;
import com.sayed.intcoretest.utils.AppUtils;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/** Application class **/
public class AppController extends Application {

    //Dec Fields
    static Retrofit retrofit;
    static int cache_size=10*1024*1024; // 10 mb

    /** On App Created **/
    @Override
    public void onCreate() {
        super.onCreate();
        initRetrofitInstance(); //init retrofit obj
        FacebookSdk.sdkInitialize(getApplicationContext());//init facebook sdk
        AppEventsLogger.activateApp(this);
    }

    //prepare singleton for retrofit
    private void initRetrofitInstance() {
        retrofit=new Retrofit.Builder().
                baseUrl(BuildConfig.HOST).
                addConverterFactory(GsonConverterFactory.create()).
                client(getClient()).
                build();
    }

    //get okhttp client with cache for 4 weeks - and cache size 10 mb
    private OkHttpClient getClient(){
        Cache cache=new Cache(getCacheDir(),cache_size);
        return new OkHttpClient.Builder().cache(cache).addInterceptor(chain -> {
            Request request=chain.request();
            if (!AppUtils.isNetworkAvailable(getApplicationContext())){
                int maxStale=60*60*24*28; //4 weeks
                request=request.newBuilder().addHeader("Cache-Control","public, only-if-cached, max-stale="+maxStale).build();
            }
            return chain.proceed(request);


        }).build();
    }

    //return retrofit
    public static Retrofit getRetrofit(){return retrofit;}

}
