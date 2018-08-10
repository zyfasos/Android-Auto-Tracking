package zyf.asos.tracking.demo;

import android.app.Application;

import zyf.asos.tracking.TrackingEngine;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TrackingEngine.init(this);
    }
}
