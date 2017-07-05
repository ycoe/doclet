package com.duoec.docs.analyse;

import com.duoec.doclet.dto.ApiItem;
import com.duoec.doclet.dto.BookSection;
import com.duoec.doclet.helper.StringHelper;
import com.duoec.docs.constant.SpringMvcDocletConstant;
import com.duoec.docs.helper.AnnotationHelper;
import com.duoec.docs.helper.TagHelper;
import com.duoec.docs.logger.Logger;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Tag;

/**
 * Created by ycoe on 16/1/27.
 */
public class ApiAnalyser {
    private static final Logger logger = Logger.getInstance(ApiAnalyser.class);

    private ApiAnalyser(){}

    public static void analyse(MethodDoc method, BookSection section){
        if(!isAvailable(method)){
            return;
        }

        Tag[] tags = method.tags();

        AnnotationDesc[] annotations = method.annotations();
        AnnotationDesc requestMapping = AnnotationHelper.getAnnotation(annotations, SpringMvcDocletConstant.ANNOTATION_REQUEST_MAPPING);
        String url = AnnotationHelper.getStringValue(requestMapping, "value");
        if(url == null) {
            url = "";
        }
        ApiItem api = section.getApiItem(section.getUrl() + url);
        String methodName = method.name();

        //保存文件名
        String path = TagHelper.getStringValue(tags, "@path", methodName);
        api.setFileName(path);

        String methodValue = AnnotationHelper.getStringValue(requestMapping, "method", "GET");
        int index = methodValue.lastIndexOf(".");
        if(index != -1){
            methodValue = methodValue.substring(index + 1);
        }
        api.setMethod(methodValue);


        String comment = method.commentText();
        String name = TagHelper.getStringValue(method.tags(), "@title", null);
        if(StringHelper.isEmpty(name)){
            name = StringHelper.firstLine(comment);
            comment = StringHelper.deleteFirstLine(comment);
        }

        if(StringHelper.isEmpty(name)){
            name = method.name();
        }
        api.setName(name);
        api.setComment(comment);
        api.setSince(TagHelper.getStringValue(tags, "@since", null));
        api.setDeprecated(TagHelper.getStringValue(tags, "@deprecated", null));
        api.setAuthor(TagHelper.getStringValue(tags, "@author", null));

        String rankStr = TagHelper.getStringValue(tags, "@rank", "255").trim();
        if(rankStr.matches("^\\d+$")){
            api.setRank(Integer.parseInt(rankStr));
        }

        //分析Response
        ResponseAnalyser.analyse(method, api);

        //分析Request
        RequestAnalyser.analyse(method, api);

        logger.info("分析完成...");
    }

    /**
     * 判断是否有效
     * @param methodDoc
     * @return
     */
    private static boolean isAvailable(MethodDoc methodDoc){
        AnnotationDesc requestMapping = AnnotationHelper.getAnnotation(methodDoc.annotations(), SpringMvcDocletConstant.ANNOTATION_REQUEST_MAPPING);
        if(requestMapping == null && !methodDoc.isPublic()){
            logger.info("无效方法{},丢弃!", methodDoc.qualifiedName());
            return false;
        }
        return true;
    }
}
