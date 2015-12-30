package cn.feibo.jodedemo.Dao;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.feibo.jodedemo.AppContext;
import cn.feibo.jodedemo.Dao.utils.SPHelper;
import cn.feibo.jodedemo.Dao.utils.TimeUtil;
import cn.feibo.jodedemo.model.DeviceInfo;
import cn.feibo.jodedemo.model.Response;
import fbcore.security.Md5;
import fbcore.task.toolbox.GetStringTask;
import fbcore.utils.Strings;

/**
 * Created by Administrator on 2015/12/28.
 */
public class UrlBuilder {

    // http://api.v.lengxiaohua.cn:8080
    // http://test.v.lengxiaohua.cn/api.php
    private static final String SERVER_HOST = "http://api.v.lengxiaohua.cn:8080";
    public static final String DEFAULTKEY = "uYz1ZS6AXNQGNlV8";
    public static final String DEFAULTCID = "10002";
    public static String cid;
    public static String key;

    public static void refreshCid() {
        SPHelper.initAuthDeviceId(AppContext.getContext());
        verifyDevice();
    }

    public static StringBuilder buildeUrl() {
        // 添加cid,uid,tms,sig,wssig,os_type,version,channel
        key = SPHelper.getAuthKey(AppContext.getContext());
        key = key == null ? DEFAULTKEY : key;

        Long cidInt = SPHelper.getDeviceId(AppContext.getContext());
        Log.i("UrlBulider", "cid  " + cidInt);
        cid = (cidInt == 0) ? DEFAULTCID : (String.valueOf(cidInt));
        String tms = TimeUtil.generateTimestamp();
        String sig = getMd5Backward16(key + tms);

        String uid = "0";
        String wssig = getMd5Backward16(SPHelper.getAuthWSkey(AppContext.getContext()) + tms);

        StringBuilder sb = new StringBuilder(SERVER_HOST);
        sb.append("?cid=").append(cid).append("&uid=").append(uid).append("&tms=").append(tms).append("&sig=")
                .append(sig).append("&wssig=").append(wssig).append("&os_type=").append(AppContext.OS_TYPE)
                .append("&version=").append(AppContext.APP_VERSION_CODE);
        // .append("&channel=").append(AppContext.UMENG_CHANNEL);

        return sb;
    }

    private static void verifyDevice() {
        String url = new StringBuilder(buildeUrl().append("&srv=1002").append("&device_id=")
                .append(AppContext.DEVICE_ID).append("&os_version=").append(AppContext.OS_VERSION).append("&model=")
                .append(AppContext.MODEL)).toString();

        String result = new GetStringTask(url).execute();
        TypeToken<Response<DeviceInfo>> token = new TypeToken<Response<DeviceInfo>>() {
        };
        Response<DeviceInfo> deviceEntity = new Gson().fromJson(result, token.getType());

        Log.i("UrlBulider", "deviceINfo:" + deviceEntity.toString() + "  rsCode:" + deviceEntity.rsCode);
        if (deviceEntity != null && deviceEntity.rsCode == ReturnCode.RS_SUCCESS) {
            Log.i("UrlBuilder", "cid-->" + deviceEntity.data.cid + "   key---> " + deviceEntity.data.key);
            SPHelper.setAuthDeviceInfo(AppContext.getContext(), deviceEntity.data.cid, deviceEntity.data.key);
        }

    }

    private static boolean isDeviceVerified() {
        long cid = SPHelper.getDeviceId(AppContext.getContext());
        return cid != 0;
    }

    public static StringBuilder getPublicParamUrl() {
        if (isDeviceVerified()) {
            verifyDevice();
        }
        return buildeUrl();
    }

    /**
     * 返回md5值的后16位.
     *
     * @param src
     * @return
     */
    @SuppressLint("DefaultLocale")
    private static String getMd5Backward16(String src) {
        String md5 = Md5.digest32(src);
        if (!Strings.isMeaningful(md5)) {
            return null;
        }
        return md5.toLowerCase().substring(16);
    }
}
