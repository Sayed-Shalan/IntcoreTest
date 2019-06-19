package com.sayed.intcoretest.app;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.sayed.intcoretest.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/** Application class **/
public class AppController extends Application {

    //Dec Fields
    Retrofit retrofit;

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
                build();
    }

    //return retrofit
    public Retrofit getRetrofit(){return retrofit;}

}
