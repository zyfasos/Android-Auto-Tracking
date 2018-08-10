package zyf.asos.tracking;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import zyf.asos.tracking.config.EventConfigEntity;
import zyf.asos.tracking.config.EventConfigManager;
import zyf.asos.tracking.event.BaseEvent;
import zyf.asos.tracking.event.PageEvent;
import zyf.asos.tracking.utils.SafeListHelper;
import zyf.asos.tracking.utils.ViewTreeProcess;


/**
 * @author zyfasos
 */
public class TrackingManager {

    private volatile static TrackingManager instance;

    private static SafeListHelper<BaseEvent> evenList;

    private static Object syncSymbol = new Object();

    /**
     * App Activity 生命周期的全局监听
     */
    private Application.ActivityLifecycleCallbacks mCallback = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        @Override
        public void onActivityStarted(Activity activity) {
            //注入 view click 事件的代理
            ViewTreeProcess.process(activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }


        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
        }
    };

    private TrackingManager() {
        evenList = new SafeListHelper<>();
    }

    public static TrackingManager getInstance() {
        synchronized (syncSymbol) {
            if (instance == null) {
                instance = new TrackingManager();
            }
        }
        return instance;
    }

    public void init(@NonNull Application application) {
        application.registerActivityLifecycleCallbacks(mCallback);
    }

    /**
     * 添加一个，埋点事件
     *
     * @param event
     */
    public void addEvent(@NonNull BaseEvent event) {
        evenList.putIfAbsent(event);
    }

    /**
     * view 的自动化埋点动作
     *
     * @param view
     */
    public void addEventByViewTag(@NonNull View view) {

        if (view != null && view.getTag() != null) {
            String tagId = (String) view.getTag();
            EventConfigEntity configBean = EventConfigManager.getInstance().getConfigEventById(tagId);

            if (configBean != null) {
                PageEvent event = new PageEvent();
                event.setEventType(configBean.type);
                event.setEventId(configBean.id);
                event.setParam(configBean.params);
                addEvent(event);
            }
        }
    }

    protected void release() {

        if (mCallback != null) {
            TrackingEngine.mApplication.unregisterActivityLifecycleCallbacks(mCallback);
        }
        instance = null;
    }


}
