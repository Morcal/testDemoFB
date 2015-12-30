package cn.feibo.jodedemo.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/12/28.
 */
public class User {

    /** 未关注*/
    public static final int RELATIONSHIP_NULL = 0;
    /** 已关注当前用户关注TA */
    public static final int RELATIONSHIP_ATTENTION = 1;
    /** 相互关注 */
    public static final int RELATIONSHIP_BOTH_ATTENTION = 3;

    public static final int RELATIONSHIP_USER_BE_ATTENTION = 2;

    @SerializedName("id")
    public long id;

    @SerializedName("nickname")
    public String nickname;

    @SerializedName("avatar")
    public String avatar;

    @SerializedName("relationship")
    public int relationship;

//    @SerializedName("detail")
//    public UserDetail detail;

    @SerializedName("is_sensation")
    public int isSensation;

    public int platform;

    public boolean isSensation() {
        return isSensation == 1;
    }

}
