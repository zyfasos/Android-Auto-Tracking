package zyf.asos.tracking.event;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Map;

/**
 * description: 事件抽象基类
 * @author zyfasos
 */
public abstract class BaseEvent {

    private String eventId;
    private String eventType;
    private Map<String, String> param;

    public void setParam(@Nullable Map<String, String> param) {
        this.param = param;
    }

    public Map<String, String> getParam() {
        return param;
    }

    public void setEventType(@NonNull String eventType) {
        this.eventType = eventType;
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventId(@NonNull String eventId) {
        this.eventId = eventId;
    }

}
