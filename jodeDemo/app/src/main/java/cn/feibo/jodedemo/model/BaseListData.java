package cn.feibo.jodedemo.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class BaseListData<E>{
    
    @SerializedName("count")
    public int count;

    @SerializedName("items")
    public List<E> items;
}
