package ua.ukraine.ukrroad.ukrroad.app;

import android.app.Application;

import ua.ukraine.ukrroad.ukrroad.helpers.HelperFactory;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HelperFactory.setHelper(getApplicationContext());
    }
    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }
}