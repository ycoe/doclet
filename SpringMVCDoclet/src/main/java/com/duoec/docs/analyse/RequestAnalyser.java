package com.duoec.docs.analyse;

import com.duoec.docs.dto.RequestFieldItem;
import com.duoec.docs.helper.StringHelper;
import com.duoec.docs.dto.ApiItem;
import com.sun.javadoc.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ycoe on 16/1/27.
 */
public class RequestAnalyser {
    public static void analyse(MethodDoc method, ApiItem api) {
        Parameter[] params = method.parameters();
        if(params == null)
            return;

        Tag[] paramTags = method.tags("@param");
        Map<String, ParamTag> paramCommentMap = new HashMap<String, ParamTag>();
        if(paramTags != null){
            for (int i = 0; i < paramTags.length; i++) {
                ParamTag tag = (ParamTag) paramTags[i];
                paramCommentMap.put(tag.parameterName(), tag);
            }
        }

        for (Parameter parameter : params){
            analyseRequestItem(api, paramCommentMap, parameter);
        }
    }

    private static void analyseRequestItem(ApiItem api, Map<String, ParamTag> paramMap, Parameter parameter) {
        RequestFieldItem item = new RequestFieldItem();
        item.setApi(api);
        String name = parameter.name();
        item.setName(name);
        if(paramMap.containsKey(name)){
            ParamTag tag = paramMap.get(name);
            item.setComment(tag.parameterComment());
        }

        AnnotationDesc[] annotations = parameter.annotations();
        for (AnnotationDesc annotationDesc : annotations) {
            String typeName = annotationDesc.annotationType().typeName();
            item.setAnnotationDesc(annotationDesc);
            AnnotationDesc.ElementValuePair[] evs = annotationDesc.elementValues();
            if(evs == null || evs.length == 0) {
                continue;
            }
            for (AnnotationDesc.ElementValuePair ev : evs){
                String evName = ev.element().name();
                if("value".equals(evName)){
                    item.setName(StringHelper.cleanQuotation(ev.value().toString()));
                }
                if("required".equals(evName)){
                    item.setRequired(!"false".equals(ev.value().toString()));
                }
                if("defaultValue".equals(evName)){
                    item.setDefaultValue(StringHelper.cleanQuotation(ev.value().toString()));
                }
            }
        }

        api.addRequestParam(item);

        /**
         * 分析元素
         */
        ClassMateAnalyser.analyse(item, parameter.type(), null);
    }
}
