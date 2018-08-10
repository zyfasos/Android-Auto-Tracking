package zyf.asos.tracking.utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import android.widget.TextView;

import zyf.asos.tracking.TrackingEngine;
import zyf.asos.tracking.TrackingManager;


/**
 * description: 遍历目标 Activity 下的 View Tree 设置埋点代理类
 *
 * @author zyfasos
 */
public class ViewTreeProcess {

    /**
     * ViewTreeProcess action
     *
     * @param activity 当前 activity
     */
    public static void process(@NonNull Activity activity) {
        View root = activity.getWindow().getDecorView();
        injectDelegateToView(root);
    }

    /**
     * 遍历当前 Activity 下所有的 view
     * 筛选得到符合条件的 view 并注入埋点代理类
     *
     * @param view the root view
     */
    private static void injectDelegateToView(@NonNull View view) {
        if (view instanceof ViewGroup) {
            ViewGroup v = (ViewGroup) view;
            if (v.isClickable()) {
                Log.d(TrackingEngine.TAG, view.getClass().getCanonicalName() + " setViewAccessibilityDelegate");
                setViewAccessibilityDelegate(view);
            }
            for (int i = 0; i < v.getChildCount(); i++) {
                View sonView = v.getChildAt(i);
                injectDelegateToView(sonView);
            }
        } else {
            if (view instanceof TextView || view instanceof ImageView) {
                if (view.isClickable()) {
                    Log.d(TrackingEngine.TAG, view.getClass().getCanonicalName() + " setViewAccessibilityDelegate");
                    setViewAccessibilityDelegate(view);
                }
            }
        }

    }

    /**
     * 在目标 view 方法里面注入埋点行为
     *
     * @param targetView the target view
     * @see View#setAccessibilityDelegate(View.AccessibilityDelegate)
     * @see View.AccessibilityDelegate#sendAccessibilityEvent(View, int)
     */
    private static void setViewAccessibilityDelegate(@NonNull final View targetView) {

        targetView.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public void sendAccessibilityEvent(View host, int eventType) {
                super.sendAccessibilityEvent(host, eventType);
                //只对 view 的 click 做注入埋点行为
                if (eventType == AccessibilityEvent.TYPE_VIEW_CLICKED) {
                    Log.d(TrackingEngine.TAG, host.getClass().getCanonicalName() + " ViewAccessibilityDelegate Click Event");
                    TrackingManager.getInstance().addEventByViewTag(targetView);
                }
            }
        });
    }


}
