package cn.feibo.jodedemo.manager;

import cn.feibo.jodedemo.Dao.JokeDao;

/**
 * Created by Administrator on 2015/12/30.
 */
public class OperateManager {
    public static void putFeedack(String content, LoadListener listener) {
        JokeDao.putFeedBack(content, new SimpleEntityListener(listener));
    }
}
