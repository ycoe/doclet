package com.duoec.test.option;

/**
 * Created by ycoe on 15/12/29.
 */
public class TagOption {
    /**
     * 标签ID
     * @demo 123
     */
    private Long id;

    /**
     * 标签名称
     * @demo 好人
     */
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
