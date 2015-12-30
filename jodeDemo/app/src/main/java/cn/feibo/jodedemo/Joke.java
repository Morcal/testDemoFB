package cn.feibo.jodedemo;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import cn.feibo.jodedemo.Dao.UrlBuilder;
import cn.feibo.jodedemo.cache.DataProvider;
import fbcore.task.AsyncTaskManager;
import fbcore.task.SyncTask;

/**
 * Created by Administrator on 2015/12/28.
 */
public class Joke extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.init(this);
        DataProvider.init(this);
        refreshCid();
        initImageLoader(getApplicationContext());
    }

    private void refreshCid() {
        AsyncTaskManager.INSTANCE.execute(new SyncTask() {
            @Override
            protected Object execute() {
                UrlBuilder.refreshCid();
                return null;
            }
        }, null);
    }

    private static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheSize(50 * 1024 * 1024);

        ImageLoader.getInstance().init(config.build());
    }

}
