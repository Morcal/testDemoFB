package cn.feibo.jodedemo.Dao.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.net.ConnectException;

/**
 * Created by Administrator on 2015/12/28.
 */
public class SPHelper {

    public enum Pref {
        APP("app"), USER("user");
        private String ns;

        private Pref(String ns) {
            this.ns = ns;
        }

        public String getNameSpace() {
            return this.ns;
        }
    }

    public static final String getAuthWSkey(Context context) {
        SharedPreferences sp = getPref(context, Pref.APP);
        return sp.getString("auth_wskey", null);
    }

    public static final Long getDeviceId(Context context) {
        SharedPreferences sp = getPref(context, Pref.APP);
        return sp.getLong("auth_devices_id", 0);
    }

    public static final String getAuthKey(Context context) {
        SharedPreferences sp = getPref(context, Pref.APP);
        return sp.getString("auth_key", null);
    }

    public static void setAuthDeviceInfo(Context context, long deviceId, String key) {
        SharedPreferences sp = getPref(context, Pref.APP);
        sp.edit().putLong("auth_devices_id", deviceId).putString("auth_key", key).commit();
    }

    public static void initAuthDeviceId(Context context) {
        SharedPreferences sp = getPref(context, Pref.APP);
        sp.edit().putLong("auth_device_id", 0).putString("auth_key", null).commit();
    }

    public static final SharedPreferences getPref(Context context, Pref pref) {
        return context.getSharedPreferences(pref.getNameSpace(), Context.MODE_PRIVATE);
    }

}
