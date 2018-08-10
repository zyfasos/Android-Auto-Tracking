package zyf.asos.tracking.aop;

import android.util.Log;
import android.view.View;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import zyf.asos.tracking.TrackingEngine;
import zyf.asos.tracking.TrackingManager;

/**
 * description: AOP 依赖注入切片类
 *
 * @author zyfasos
 */
@Aspect
public class AutoTrackingAspectJ {

    /**
     * 切入点点为 view 的 onClick 方法
     *
     * @param view
     */
    @Pointcut("execution(* *.onClick(android.view.View)) && args(view) && !within(com.xx.xxx.xxx.*) ")
    public void viewOnClick(View view) {

    }

    /**
     * 触发点为 onClick 调用之后触发
     *
     * @param joinPoint
     * @param view
     */
    @After("viewOnClick(view)")
    public void addEvent(JoinPoint joinPoint, View view) {
        Log.d(TrackingEngine.TAG, String.format("viewOnClick : view = %s", view.getClass().getCanonicalName()));
        TrackingManager.getInstance().addEventByViewTag(view);
    }

}
