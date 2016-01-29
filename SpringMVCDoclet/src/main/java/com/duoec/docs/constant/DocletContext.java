package com.duoec.docs.constant;

import com.duoec.docs.dto.Book;
import com.duoec.docs.dto.ClassMate;

import java.util.*;

/**
 * Created by ycoe on 16/1/27.
 */
public class DocletContext {
    /**
     * 保存class全名与ClassMate的关系
     */
    private static Map<String, ClassMate> CLASS_MAP = new HashMap<String, ClassMate>();

    /**
     * Book Map
     */
    private static Map<String, Book> BOOK_MAP = new HashMap<String, Book>();

    private DocletContext(){}

    /**
     * 添加ClassMate实例
     * @param classMate
     */
    public static void addClassMate(ClassMate classMate){
        CLASS_MAP.put(classMate.getId(), classMate);
    }

    /**
     * 获取ClassMate实例
     * @param id
     * @return
     */
    public static ClassMate getClassMate(String id){
        return CLASS_MAP.get(id);
    }

    /**
     * 获取Book
     * @param bookName
     * @return
     */
    public static Book getBook(String bookName){
        if(BOOK_MAP.containsKey(bookName)){
            return BOOK_MAP.get(bookName);
        }
        Book book = new Book(bookName);
        book.setPath(DocletConstant.OUTPUT_PATH + "/" + bookName);
        book.setUrl(DocletConstant.WEB_APP_CONTEXT);
        BOOK_MAP.put(bookName, book);
        return book;
    }

    /**
     * 获取所有Book Name
     * @return
     */
    public static List<String> getBookNames(){
        Set<String> ks = BOOK_MAP.keySet();
        List<String> bookNames = new ArrayList<String>();
        for (String k : ks){
            bookNames.add(k);
        }
        return bookNames;
    }
}
