package com.duoec.docs.doclet;

import com.duoec.doclet.constant.DocletConstant;
import com.duoec.doclet.dto.ClassMate;
import com.duoec.doclet.dto.FieldItem;
import com.duoec.doclet.helper.StringHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ycoe on 16/1/28.
 */
public class JsonFormater {
    public static String toJson(ClassMate mate){
        FieldItem fieldItem = new FieldItem();
        fieldItem.cloneClassMate(mate);
        return toJson(fieldItem);
    }

    public static String toJson(FieldItem fieldItem){
        List<String> paths = new ArrayList<String>();
        return toJson(fieldItem, paths);
    }

    public static String toJson(FieldItem fieldItem, List<String> paths){
        int level = paths.size();
        StringBuilder sb = new StringBuilder();
        String fullTypeName = fieldItem.getFormatedTypeName();
        if(paths.contains(fullTypeName)){
            //循环...
            sb.append(StringHelper.loopTab(level + 1));
            sb.append("//{...} 循环 @type " + fullTypeName);
            return sb.toString();
        }

        List<String> myPaths = new ArrayList<String>();
        myPaths.addAll(paths);

        myPaths.add(fullTypeName);

        List<FieldItem> items = fieldItem.getItems();
        String name = fieldItem.getName();
        if(items.isEmpty()){
            //如果是List
            sb.append(DocletConstant.TURN_LINE);
            sb.append(StringHelper.loopTab(level + 1));
            sb.append("\"");
            sb.append(name);
            sb.append("\"");
            sb.append(" : ");
            if("java.util.List".equals(fullTypeName) || fullTypeName.startsWith("List<")){
                sb.append(getListDemoValue(fieldItem, myPaths));
            }else{
                sb.append(getDefaultDemoValue(fieldItem));
                if(!fieldItem.isLastOne()){
                    sb.append(",");
                }
                sb.append(getCommentText(fieldItem));
            }
        }else{
            if(StringHelper.isNotEmpty(name)){
                sb.append(DocletConstant.TURN_LINE);
                sb.append(StringHelper.loopTab(level + 1));
                sb.append("\"");
                sb.append(name);
                sb.append("\"");
                sb.append(" : ");
            }
            if(level > 0){
                sb.append(StringHelper.loopTab(level + 1));
            }
            sb.append("{");
            sb.append(getCommentText(fieldItem));
            items.get(items.size() - 1).setLastOne(true);
            for (FieldItem item : items){
                sb.append(toJson(item, myPaths));
            }

            sb.append(DocletConstant.TURN_LINE);
            sb.append(StringHelper.loopTab(level + 1));
            sb.append("}");
        }
        return sb.toString();
    }

    private static String getCommentText(FieldItem fieldItem) {
        StringBuilder sb = new StringBuilder();
        sb.append(" //");
        String comment = fieldItem.getComment();
        if(StringHelper.isNotEmpty(comment)){
            comment = comment.replaceAll("[\\r|\\n]", " ");
            sb.append(comment);
        }

        String since = fieldItem.getSince();
        if(StringHelper.isNotEmpty(since)){
            sb.append(" @since ");
            sb.append(since);
        }

        String deprecated = fieldItem.getDeprecated();
        if(StringHelper.isNotEmpty(deprecated)){
            sb.append(" @deprecated ");
            sb.append(deprecated);
        }

        sb.append(" @type ");
        sb.append(fieldItem.getFormatedTypeName());
        return sb.toString();
    }

    private static String getListDemoValue(FieldItem fieldItem, List<String> myPaths) {
        int level = myPaths.size();
        List<ClassMate> typeArguments = fieldItem.getTypeArguments();
        ClassMate mate = typeArguments.get(0);
        if("java.lang".equals(mate.getPackageName())) {
            return fieldItem.getDemo();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(getCommentText(fieldItem));
        sb.append(DocletConstant.TURN_LINE);

        FieldItem item = new FieldItem();
        item.cloneClassMate(mate);
        sb.append(JsonFormater.toJson(item, myPaths));

        sb.append(DocletConstant.TURN_LINE);
        sb.append(StringHelper.loopTab(level));
        sb.append("]");

        if(!fieldItem.isLastOne()){
            sb.append(",");
        }
        return sb.toString();
    }

    private static String getDefaultDemoValue(FieldItem field) {
        String demo = field.getDemo();
        if(demo == null){
            return "null";
        }
        if("java.lang.String".equals(field.getFullName())){
            return "\"" + demo + "\"";
        }
        return demo;
    }

}
