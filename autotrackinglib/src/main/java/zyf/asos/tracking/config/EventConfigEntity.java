package zyf.asos.tracking.config;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Map;

/**
 * @author zyfasos
 */
public class EventConfigEntity {

    @NonNull
    public String id;
    @NonNull
    public String type;
    @Nullable
    public String description;
    @Nullable
    public Map<String, String> params;

}
