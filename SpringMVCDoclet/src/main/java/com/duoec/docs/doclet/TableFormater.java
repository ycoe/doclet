package com.duoec.docs.doclet;

import com.duoec.doclet.constant.DocletConstant;
import com.duoec.doclet.dto.ClassMate;
import com.duoec.doclet.dto.FieldItem;
import com.duoec.doclet.helper.StringHelper;
import com.duoec.docs.constant.SpringMvcDocletConstant;
import com.duoec.docs.dto.SpringMvcRequestFieldItem;
import com.sun.javadoc.AnnotationDesc;

import java.util.List;

/**
 * Created by ycoe on 16/1/29.
 */
public class TableFormater {
    public static String toMarkdown(List<SpringMvcRequestFieldItem> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("|参数名|类型|来源|是否必填|说明|");
        sb.append(DocletConstant.TURN_LINE);
        sb.append("|:--|:--:|:--:|:--:|:--|");
        sb.append(DocletConstant.TURN_LINE);
        for (SpringMvcRequestFieldItem item : items){
            sb.append(toRowMarkdown(item));
            sb.append(DocletConstant.TURN_LINE);
        }

        //属性补充说明
        for (SpringMvcRequestFieldItem item : items){
            List<FieldItem> children = item.getItems();
            if(!children.isEmpty()){
                sb.append(getAttrs(item.getName(), item));
            }
            List<ClassMate> typeArguments = item.getTypeArguments();
            if(!typeArguments.isEmpty()){
                for (ClassMate mate : typeArguments){
                    sb.append(getAttrs(item.getName(), mate));
                }
            }
        }
        return sb.toString();
    }

    private static String getAttrs(String name, ClassMate item){
        StringBuilder sb = new StringBuilder();
        sb.append(DocletConstant.TURN_LINE_2);
        sb.append(name);
        sb.append("参数 @type `");
        sb.append(item.getFormatedTypeName());
        sb.append("` 属性");
        sb.append(DocletConstant.TURN_LINE_2);
        sb.append("```JSON");
        sb.append(DocletConstant.TURN_LINE);
        sb.append(JsonFormater.toJson(item));
        sb.append(DocletConstant.TURN_LINE);
        sb.append("```");
        return sb.toString();
    }

    private static String toRowMarkdown(SpringMvcRequestFieldItem item){
        StringBuilder sb = new StringBuilder();
        sb.append("|");
        sb.append(item.getName());
        sb.append("|");
        sb.append(item.getFormatedTypeName());
        sb.append("|");
        sb.append(getFormatedAnnotationDesc(item.getAnnotationDesc()));
        sb.append("|");
        sb.append(item.isRequired() ? "true" : "false");
        sb.append("|");
        String comment = item.getComment();
        sb.append(StringHelper.isNullThenSet(comment, ""));
        sb.append("|");
        return sb.toString();
    }

    private static String getFormatedAnnotationDesc(AnnotationDesc annotationDesc){
        if(annotationDesc == null)
            return "";
        String name = annotationDesc.toString();
        return "@" + name.replaceAll("^.*\\.(\\w+)$", "$1");
    }
}
