package com.duoec.docs.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ycoe on 16/1/27.
 */
public class ApiItem {
    /**
     * 接口级别(缩进)
     */
    private int level;

    /**
     * 接口名称
     */
    private String name;

    /**
     * 接口URL
     */
    private String url;

    /**
     * 注释
     */
    private String comment;

    /**
     * since注释
     */
    private String since;

    /**
     * author注释
     */
    private String author;

    /**
     * deprecated 注释
     */
    private String deprecated;

    /**
     * 请求方法:GET / POST / DELETE...
     */
    private String method;

    /**
     * 排序,越小越前
     */
    private int rank = 255;

    /**
     * 响应
     */
    private ResponseFieldItem response;

    /**
     * 请求参数
     */
    private List<RequestFieldItem> requestParams = new ArrayList<RequestFieldItem>();

    /**
     * Book Section
     */
    private BookSection section;

    /**
     * 文件名可以保证section中唯一,不包含后缀
     */
    private String fileName;

    public ApiItem(String url) {
        this.setUrl(url);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSince() {
        return since;
    }

    public void setSince(String since) {
        this.since = since;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDeprecated() {
        return deprecated;
    }

    public void setDeprecated(String deprecated) {
        this.deprecated = deprecated;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public ResponseFieldItem getResponse() {
        return response;
    }

    public void setResponse(ResponseFieldItem response) {
        this.response = response;
    }

    public List<RequestFieldItem> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(List<RequestFieldItem> requestParams) {
        this.requestParams = requestParams;
    }

    public BookSection getSection() {
        return section;
    }

    public void setSection(BookSection section) {
        this.section = section;
    }

    public void addRequestParam(RequestFieldItem item) {
        requestParams.add(item);
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
