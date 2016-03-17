package com.duoec.doclet.dto;

import com.duoec.doclet.helper.StringHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ycoe on 16/1/27.
 */
public class ClassMate {
    /**
     * ID
     */
    private String id;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 类名
     */
    private String className;

    /**
     * java文件全路径
     */
    private String javaFilePath;

    /**
     * 子项
     */
    private List<FieldItem> items = new ArrayList<FieldItem>();

    /**
     * 泛型类型
     */
    private List<ClassMate> typeArguments = new ArrayList<ClassMate>();

    public ClassMate(){}

    public ClassMate(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        String packageName = getPackageName();
        if(StringHelper.isNotEmpty(packageName)){
            return packageName + "." + getClassName();
        }else{
            return getClassName();
        }
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getJavaFilePath() {
        return javaFilePath;
    }

    public void setJavaFilePath(String javaFilePath) {
        this.javaFilePath = javaFilePath;
    }

    public List<FieldItem> getItems() {
        return items;
    }

    public void setItems(List<FieldItem> items) {
        this.items = items;
    }

    public List<ClassMate> getTypeArguments() {
        return typeArguments;
    }

    public void setTypeArguments(List<ClassMate> typeArguments) {
        this.typeArguments = typeArguments;
    }

    /**
     * 复制ClassMate
     * @param mate
     */
    public void cloneClassMate(ClassMate mate) {
        this.setJavaFilePath(mate.getJavaFilePath());
        this.setClassName(mate.getClassName());
        this.setPackageName(mate.getPackageName());
        this.setItems(mate.getItems());
        this.setTypeArguments(mate.getTypeArguments());
    }

    /**
     * 添加一个泛型
     * @param mate
     */
    public void addTypeArgument(ClassMate mate) {
        typeArguments.add(mate);
    }

    public void addItem(FieldItem item) {
        items.add(item);
    }

    public String getFormatedTypeName() {
        String packageName = getPackageName();
        if("java.lang".equals(packageName) || "java.util".equals(packageName)){
            return getClassName() + argumentTypesStr();
        }
        return getFullName() + argumentTypesStr();
    }

    private String argumentTypesStr(){
        List<ClassMate> argumentTypes = getTypeArguments();
        if(argumentTypes.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<");
        int size = argumentTypes.size();
        for (int i = 0; i < size; i++) {
            ClassMate mate = argumentTypes.get(i);
            if(i != 0 && i != size - 1){
                sb.append(", ");
            }
            sb.append(mate.getFormatedTypeName());
        }
        sb.append(">");
        return sb.toString();
    }
}
