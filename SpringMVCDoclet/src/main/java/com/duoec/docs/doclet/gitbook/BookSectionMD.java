package com.duoec.docs.doclet.gitbook;

import com.duoec.docs.constant.DocletConstant;
import com.duoec.docs.dto.ApiItem;
import com.duoec.docs.dto.BookSection;

import java.util.List;

/**
 * Created by ycoe on 16/1/28.
 */
public class BookSectionMD {
    public static String getSectionSummaryString(BookSection section){
        StringBuilder sb = new StringBuilder();
        sb.append("* [");
        sb.append(section.getName());
        sb.append("](");
        sb.append(section.getPath());
        sb.append("/README.md)");
        sb.append(DocletConstant.TURN_LINE);

        List<ApiItem> apis = section.getItems();
        if(apis != null){
            for(ApiItem api : apis){
                sb.append(ApiMD.getApiSummaryString(api));
            }
        }

        return sb.toString();
    }
}
