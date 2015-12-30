package cn.feibo.jodedemo.manager;

import cn.feibo.jodedemo.Dao.IEntityListener;
import cn.feibo.jodedemo.Dao.JokeDao;
import cn.feibo.jodedemo.model.BaseListData;
import cn.feibo.jodedemo.model.Feedback;

/**
 * Created by Administrator on 2015/12/28.
 */
public class FeedbackManager {
    public void loadFeedback(IEntityListener<BaseListData<Feedback>> listener){
        JokeDao.getFeedBack(listener,false);
    }
}
