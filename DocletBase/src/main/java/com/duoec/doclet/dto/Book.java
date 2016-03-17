package com.duoec.doclet.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ycoe on 16/1/27.
 */
public class Book {
    /**
     * 名称
     */
    private String name;

    /**
     * 章节
     */
    private List<BookSection> sections = new ArrayList<BookSection>();

    /**
     * URL
     */
    private String url;

    /**
     * 保存路径(全)
     */
    private String path;

    public Book(String bookName) {
        setName(bookName);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookSection> getSections() {
        return sections;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 添加章节
     * @param section
     */
    public void addSection(BookSection section) {
        sections.add(section);
    }

    /**
     * 获取章节,如果不存在则添加
     * @param sectionName
     * @return
     */
    public BookSection getSection(String sectionName) {
        for (BookSection section : sections){
            if(section.getName().equals(sectionName)){
                return section;
            }
        }
        BookSection section = new BookSection(sectionName);
        section.setBook(this);
        section.setLevel(1);

        sections.add(section);
        return section;
    }
}
