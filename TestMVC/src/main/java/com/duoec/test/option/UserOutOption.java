package com.duoec.test.option;

import java.util.List;

/**
 * Created by ycoe on 15/12/29.
 */
public class UserOutOption {
    /**
     * 用户ID
     * @since 1.7
     * @deprecated v1.9
     * @demo 12
     */
    private int id;

    /**
     * 用户名称
     * @demo 张三
     */
    private String name;

    /**
     * 标签
     */
    private List<TagOption> tags;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TagOption> getTags() {
        return tags;
    }

    public void setTags(List<TagOption> tags) {
        this.tags = tags;
    }
}
