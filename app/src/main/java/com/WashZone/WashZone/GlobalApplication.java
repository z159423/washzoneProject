package com.WashZone.WashZone;

import android.app.Activity;
import android.app.Application;

import com.kakao.auth.KakaoSDK;

public class GlobalApplication extends Application {

    private static volatile GlobalApplication obj = null;
    private static volatile Activity currentActivity = null;
    private static GlobalApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        obj = this;
        instance = this;

        //KakaoSdk.init(this, "{f67e005396d538b6731b5817f03c1811}");
        KakaoSDK.init(new KakaoSDKAdapter());


    }

    /*public static GlobalApplication getGlobalApplicationContext() {
        return obj;
    }*/

    @Override

    public void onTerminate() {

        super.onTerminate();

        instance = null;

    }

    public static GlobalApplication getGlobalApplicationContext() {

        if (instance == null) {

            throw new IllegalStateException("This Application does not inherit com.kakao.GlobalApplication");

        }



        return instance;

    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    // Activity가 올라올때마다 Activity의 onCreate에서 호출해줘야한다.
    public static void setCurrentActivity(Activity currentActivity) {
        GlobalApplication.currentActivity = currentActivity;
    }


}

