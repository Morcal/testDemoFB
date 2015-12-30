package cn.feibo.jodedemo.Dao;

import com.google.gson.reflect.TypeToken;

import java.util.Objects;

import cn.feibo.jodedemo.AppContext;
import cn.feibo.jodedemo.model.BaseListData;
import cn.feibo.jodedemo.model.Feedback;
import cn.feibo.jodedemo.model.Response;

/**
 * Created by Administrator on 2015/12/28.
 */
public class JokeDao {

    // 1102 获取意见反馈信息
    public static void getFeedBack(IEntityListener<BaseListData<Feedback>> listener, boolean cache) {
        String url = new StringBuilder().append("&srv=1102").toString();
        Dao.getEntity(url, new TypeToken<Response<BaseListData<Feedback>>>() {
        }, listener, cache);
    }

    // 1101 提交反馈
    public static void putFeedBack(String content,IEntityListener<Object> listener){
        String url = new StringBuilder().append("&srv=1101").append("&os_version=").append(AppContext.OS_VERSION)
                .append("&content=").append(content).toString();
        Dao.getEntity(url, new TypeToken<Response<Object>>() {
        },listener,false);
    }
}
