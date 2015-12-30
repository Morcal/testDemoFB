package cn.feibo.jodedemo.Dao.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * Created by Administrator on 2015/12/28.
 */
public class DeviceUid {

    public static String generate(Context context) {
        String uid = null;

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        uid = tm.getDeviceId();

        if (!TextUtils.isEmpty(uid)) {
            return uid;
        }
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wm.getConnectionInfo();
        uid = info.getMacAddress();

        return uid;
    }
}
