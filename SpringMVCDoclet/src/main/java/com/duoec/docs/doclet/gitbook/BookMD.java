package com.duoec.docs.doclet.gitbook;

import com.duoec.docs.constant.DocletConstant;
import com.duoec.docs.dto.BookSection;
import com.duoec.docs.dto.Book;

import java.util.List;

/**
 * Created by ycoe on 16/1/28.
 */
public class BookMD {
    public static String getSummaryString(Book book) {
        StringBuilder sb = new StringBuilder();
        List<BookSection> sections = book.getSections();
        if(sections != null){
            for (BookSection section : sections){
                sb.append(BookSectionMD.getSectionSummaryString(section));
                sb.append(DocletConstant.TURN_LINE);
            }
        }
        return sb.toString();
    }
}
