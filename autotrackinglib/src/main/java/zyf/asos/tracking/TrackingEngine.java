package zyf.asos.tracking;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;

import zyf.asos.tracking.config.EventConfigManager;
import zyf.asos.tracking.event.BaseEvent;

/**
 * @author zyfasos
 */
public final class TrackingEngine {

    public final static String TAG = "TRACKING";

    public static Application mApplication;

    public static void init(@NonNull final Application application) {

        mApplication = application;
        TrackingManager.getInstance().init(application);

        //开启一个异步 Handler 线程 ，去处理需要异步初始化的动作
        HandlerThread mHandlerThread = new HandlerThread(TAG);
        mHandlerThread.start();
        Handler initHandler = new Handler(mHandlerThread.getLooper());

        initHandler.post(new Runnable() {
            @Override
            public void run() {
                EventConfigManager.getInstance().init();
            }
        });
    }

    public static Context getContext() {
        return mApplication.getApplicationContext();
    }

    public static void addEvent(@NonNull BaseEvent event) {
        TrackingManager.getInstance().addEvent(event);
    }

    public static void release() {
        TrackingManager.getInstance().release();
    }

}
