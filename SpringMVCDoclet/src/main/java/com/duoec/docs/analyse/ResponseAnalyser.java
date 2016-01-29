package com.duoec.docs.analyse;

import com.duoec.docs.dto.ResponseFieldItem;
import com.duoec.docs.helper.TagHelper;
import com.duoec.docs.dto.ApiItem;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Type;

/**
 * Created by ycoe on 16/1/27.
 */
public class ResponseAnalyser {
    public static void analyse(MethodDoc method, ApiItem api) {
        ResponseFieldItem responseField = new ResponseFieldItem();
        responseField.setApi(api);

        String comment = TagHelper.getStringValue(method.tags(), "@return", null);
        responseField.setComment(comment);

        Type returnType = method.returnType();

        /**
         * 分析元素
         */
        ClassMateAnalyser.analyse(responseField, returnType, null);

        api.setResponse(responseField);
    }
}
