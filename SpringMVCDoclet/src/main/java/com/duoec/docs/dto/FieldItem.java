package com.duoec.docs.dto;

/**
 * Created by ycoe on 16/1/27.
 */
public class FieldItem extends ClassMate{
    /**
     * 名称
     */
    private String name;

    /**
     * since
     */
    private String since;

    /**
     * deprecated
     */
    private String deprecated;

    /**
     * 测试数据
     */
    private String demo;

    /**
     * 注释
     */
    private String comment;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 是否必须
     */
    private boolean required = false;

    /**
     * 是否最后一个元素
     */
    protected boolean isLastOne = false;

    /**
     * 所属API
     */
    private ApiItem api;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSince() {
        return since;
    }

    public void setSince(String since) {
        this.since = since;
    }

    public String getDeprecated() {
        return deprecated;
    }

    public void setDeprecated(String deprecated) {
        this.deprecated = deprecated;
    }

    public String getDemo() {
        return demo;
    }

    public void setDemo(String demo) {
        this.demo = demo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isLastOne() {
        return isLastOne;
    }

    public void setLastOne(boolean lastOne) {
        isLastOne = lastOne;
    }

    public void setApi(ApiItem api) {
        this.api = api;
    }

    public ApiItem getApi() {
        return api;
    }
}
