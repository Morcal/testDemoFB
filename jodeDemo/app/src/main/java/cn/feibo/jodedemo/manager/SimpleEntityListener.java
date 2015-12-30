package cn.feibo.jodedemo.manager;

import cn.feibo.jodedemo.Dao.IEntityListener;
import cn.feibo.jodedemo.Dao.ReturnCode;
import cn.feibo.jodedemo.model.Response;

public class SimpleEntityListener implements IEntityListener<Object> {

    private LoadListener listener;

    public SimpleEntityListener(LoadListener listener) {
        this.listener = listener;
    }

    @Override
    public void result(Response<Object> entity) {
        if (entity.rsCode == ReturnCode.RS_SUCCESS) {
            listener.onSuccess();
            return;
        }
        listener.onFail(entity.rsCode);
    }

}
