package cn.feibo.jodedemo.manager;

public interface LoadListener {
    void onSuccess();

    void onFail(int code);
}