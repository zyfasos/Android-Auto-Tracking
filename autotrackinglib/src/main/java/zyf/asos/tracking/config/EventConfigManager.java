package zyf.asos.tracking.config;

import android.support.annotation.NonNull;
import android.support.annotation.XmlRes;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;

import zyf.asos.tracking.TrackingEngine;
import zyf.asos.tracking.lib.R;
import zyf.asos.tracking.utils.Constants;

/**
 * description:解析 xml 配置得到埋点配置集合
 * @author zyfasos
 */
public class EventConfigManager {

    private static EventConfigManager instance;

    private static SparseArray<EventConfigEntity> configEventCollections = new SparseArray<>();

    public static EventConfigManager getInstance() {
        if (instance == null) {
            instance = new EventConfigManager();
        }
        return instance;
    }

    public void init() {
        parseXml(R.xml.event_group);
        Log.d(TrackingEngine.TAG, "configEventCollections" + configEventCollections.size());
    }

    private void parseXml(@XmlRes int xmlRes) {

        XmlPullParser xmlParser = TrackingEngine.getContext().getResources().getXml(xmlRes);

        try {
            int eventType = xmlParser.getEventType();
            String tagName;
            EventConfigEntity configEntity = null;
            HashMap<String, String> params = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tagName = xmlParser.getName();

                        if (tagName.equals(Constants.EVENT_TAG)) {
                            configEntity = new EventConfigEntity();
                            configEntity.id = xmlParser.getAttributeValue(null, Constants.ATTR_ID);
                            configEntity.type = xmlParser.getAttributeValue(null, Constants.ATTR_TYPE);
                            configEntity.description = xmlParser.getAttributeValue(null, Constants.ATTR_DESCRIPTION);
                        } else if (tagName.equals(Constants.PARAMETERS_TAG)) {
                            params = new HashMap<>();
                        } else if (tagName.equals(Constants.PARAMETER_TAG)) {
                            String key = xmlParser.getAttributeValue(null, Constants.ATTR_KEY);
                            String value = xmlParser.getAttributeValue(null, Constants.ATTR_VALUE);
                            params.put(key, value);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tagName = xmlParser.getName();

                        if (Constants.PARAMETERS_TAG.equals(tagName)) {
                            configEntity.params = params;
                        } else if (tagName.equals(Constants.EVENT_TAG)) {
                            configEventCollections.append(Integer.valueOf(configEntity.id), configEntity);
                        }
                        break;
                    default:
                        break;
                }
                eventType = xmlParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public EventConfigEntity getConfigEventById(@NonNull String id) {

        if (TextUtils.isDigitsOnly(id)) {
            int key = Integer.valueOf(id);
            return configEventCollections.get(key);
        }
        return null;
    }
}
