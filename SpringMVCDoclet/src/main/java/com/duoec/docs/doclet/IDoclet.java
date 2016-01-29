package com.duoec.docs.doclet;

import com.duoec.docs.dto.Book;

/**
 * Created by ycoe on 16/1/27.
 */
public interface IDoclet {
    /**
     * 创建文档
     * @param book
     */
    void createDocs(Book book);
}
