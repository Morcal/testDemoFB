package cn.feibo.jodedemo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import cn.feibo.jodedemo.Dao.utils.DeviceUid;
import fbcore.security.Md5;
import fbcore.utils.Utils;

/**
 * Created by Administrator on 2015/12/28.
 */
public class AppContext {
    public static String APP_VERSION_CODE;
    public static String DEVICE_ID;
    public static String UMENG_CHANNEL;
    private static Context context;
    public static String OS_TYPE = "3";
    public static String OS_VERSION;
    public static String MODEL;

    private static AppContext appContext;

    public static Context getContext() {
        return context;
    }

    public synchronized static void init(Context context) {
        initAppParams(context);
        DirContext.getInstance().initCacheDir(context);
    }

    public static void initAppParams(Context context) {
        AppContext.context = context;
        APP_VERSION_CODE = String.valueOf(Utils.getAppVersionCode(context));
        DEVICE_ID = Md5.digest32(DeviceUid.generate(context));

        MODEL = Build.MODEL;
        MODEL = MODEL.replace(" ", "");
        OS_VERSION = Build.VERSION.RELEASE;
        ApplicationInfo info;
        try {
            info = AppContext.getContext().getPackageManager()
                    .getApplicationInfo(AppContext.context.getPackageName(), PackageManager.GET_META_DATA);
            // UMENG_CHANNEL = info.metaData.getString("UMENG_CHANNEL");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

}
