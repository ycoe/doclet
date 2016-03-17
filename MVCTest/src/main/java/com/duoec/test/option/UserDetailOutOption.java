package com.duoec.test.option;

import java.util.List;

/**
 * Created by ycoe on 15/12/29.
 */
public class UserDetailOutOption extends UserOutOption{
    /**
     * 全名
     * @since 1.7
     * @deprecated v1.9
     * @demo 张三李四
     */
    private String fullName;

    /**
     * 下属
     */
    private List<UserDetailOutOption> staff;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<UserDetailOutOption> getStaff() {
        return staff;
    }

    public void setStaff(List<UserDetailOutOption> staff) {
        this.staff = staff;
    }
}
