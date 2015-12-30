package cn.feibo.jodedemo.Dao;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.feibo.jodedemo.AppContext;
import cn.feibo.jodedemo.Dao.utils.SPHelper;
import cn.feibo.jodedemo.model.Response;
import fbcore.task.AsyncTaskManager;
import fbcore.task.SyncTask;
import fbcore.task.TaskFailure;
import fbcore.task.TaskHandler;
import fbcore.task.toolbox.GetStringTask;

/**
 * Created by Administrator on 2015/12/28.
 */
public class Dao<T> {
    private static void getAsyncString(final String paramUrl, TaskHandler handler) {
        AsyncTaskManager.INSTANCE.execute(new SyncTask() {
            @Override
            protected Object execute() {
                String url = UrlBuilder.getPublicParamUrl().append(paramUrl).toString();

                Log.i("URL--------> ", url);
                return new GetStringTask(url).execute();
            }
        }, handler);
    }

    public static <T> void getEntity(String paramUrl, final TypeToken<Response<T>> token, final IEntityListener<T> listener,
            boolean cache) {
        String cid = UrlBuilder.DEFAULTCID;
        String key = UrlBuilder.DEFAULTKEY;
        if (UrlBuilder.cid == cid || UrlBuilder.key == key) {
            UrlBuilder.cid = String.valueOf(SPHelper.getDeviceId(AppContext.getContext()));
            UrlBuilder.key = SPHelper.getAuthKey(AppContext.getContext());
            UrlBuilder.buildeUrl();
        }
        getAsyncString(paramUrl, new TaskHandler() {
            @Override
            public void onSuccess(Object result) {
                Log.i("Dao", "成功");
                Log.i("Result:", result + "");
                Response<T> response = new Gson().fromJson((String) result, token.getType());
                Log.i("Dao","response data"+response.data);
                listener.result(response);
            }

            @Override
            public void onFail(TaskFailure taskFailure) {
                Log.i("Dao", "失败");
            }

            @Override
            public void onProgressUpdated(Object... objects) {

            }
        });
    }
}
