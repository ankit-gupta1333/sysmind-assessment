package com.example.marvelcharacters;

import android.content.Context;

import com.example.marvelcharacters.entity.MyObjectBox;

import androidx.multidex.MultiDexApplication;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;


public class MyApplication extends MultiDexApplication {

    private static Context context;
    public static BoxStore boxStore;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
        boxStore = MyObjectBox.builder().androidContext(MyApplication.this).build();
        if (BuildConfig.DEBUG) {
            new AndroidObjectBrowser(boxStore).start(this);
        }
    }
    public BoxStore getBoxStore() {
        return boxStore;
    }
    public static Context getAppContext() {
        return MyApplication.context;
    }
}