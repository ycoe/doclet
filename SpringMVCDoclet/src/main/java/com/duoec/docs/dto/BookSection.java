package com.duoec.docs.dto;

import com.duoec.docs.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ycoe on 16/1/27.
 */
public class BookSection {
    private static final Logger logger = Logger.getInstance(BookSection.class);

    /**
     * 接口
     */
    private List<ApiItem> items = new ArrayList<ApiItem>();

    /**
     * 名称
     */
    private String name;

    /**
     * 注释
     */
    private String comment;

    /**
     * 全路径
     */
    private String path;

    /**
     * 路径
     */
    private String url;

    /**
     * 层级
     */
    private int level = 1;

    /**
     * 排序,越小越前
     */
    private int rank = 255;

    /**
     * Book
     */
    private Book book;

    public BookSection(String name) {
        this.name = name;
    }

    public List<ApiItem> getItems() {
        return items;
    }

    public void setItems(List<ApiItem> items) {
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    public void addApi(ApiItem apiItem) {
        items.add(apiItem);
    }

    public ApiItem getApiItem(String url) {
        for (ApiItem apiItem : items){
            if(apiItem.getUrl().equals(url)){
                return apiItem;
            }
        }

        logger.info("添加Api: {}", url);
        ApiItem apiItem = new ApiItem(url);
        apiItem.setLevel(getLevel() + 1);
        apiItem.setSection(this);
        this.addApi(apiItem);

        return apiItem;
    }
}
