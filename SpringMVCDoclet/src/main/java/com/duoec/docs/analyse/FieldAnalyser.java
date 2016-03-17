package com.duoec.docs.analyse;

import com.duoec.doclet.dto.ClassMate;
import com.duoec.doclet.dto.FieldItem;
import com.duoec.docs.constant.SpringMvcDocletConstant;
import com.duoec.docs.helper.AnnotationHelper;
import com.duoec.docs.helper.TagHelper;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.Type;

import java.util.Map;

/**
 * Created by ycoe on 16/1/28.
 */
public class FieldAnalyser {

    private FieldAnalyser(){}

    /**
     * 解析类属性
     * @param mate
     * @param field
     * @param genericMap
     */
    public static void analyse(ClassMate mate, FieldDoc field, Map<String, Type> genericMap){
        FieldItem item = new FieldItem();

        item.setComment(field.commentText());
        item.setName(field.name());

        //Tags
        item.setDemo(TagHelper.getStringValue(field.tags("@demo")));
        item.setDeprecated(TagHelper.getStringValue(field.tags("@deprecated")));
        item.setSince(TagHelper.getStringValue(field.tags("@since")));

        //优先判断是否有 @NotNull
        AnnotationDesc annotation = AnnotationHelper.getAnnotation(field.annotations(), SpringMvcDocletConstant.ANNOTATION_NOT_NULL);
        if(annotation != null){
            item.setRequired(true);
        }else{
            item.setRequired("true".equals(TagHelper.getStringValue(field.tags("@required"))));
        }

        ClassMateAnalyser.analyse(item, field.type(), genericMap);
        mate.addItem(item);
    }
}
