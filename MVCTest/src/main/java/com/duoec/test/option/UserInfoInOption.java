package com.duoec.test.option;

import java.util.List;

/**
 * Created by ycoe on 15/12/29.
 */
public class UserInfoInOption {
    /**
     * 用户ID
     * @demo 12
     */
    private int userId;

    /**
     * 用户名称，可以多个
     * @demo ["张三", "李四"]
     */
    private List<String> names;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
}
