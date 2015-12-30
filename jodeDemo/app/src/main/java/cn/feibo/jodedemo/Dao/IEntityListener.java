package cn.feibo.jodedemo.Dao;

import cn.feibo.jodedemo.model.Response;

public interface IEntityListener<T>{
    void result(Response<T> response);
}
