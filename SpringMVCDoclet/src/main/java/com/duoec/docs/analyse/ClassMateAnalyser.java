package com.duoec.docs.analyse;

import com.duoec.doclet.dto.ClassMate;
import com.duoec.doclet.dto.FieldItem;
import com.duoec.docs.constant.DocletContext;
import com.duoec.docs.helper.ClassDocHelper;
import com.duoec.docs.logger.Logger;
import com.sun.javadoc.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ycoe on 16/1/27.
 */
public class ClassMateAnalyser {
    private static final Logger logger = Logger.getInstance(ClassMateAnalyser.class);

    private static Pattern CLASS_FULL_NAME_PATTERN = Pattern.compile("^(.*)\\.([^\\.]+)$");

    private ClassMateAnalyser(){}

    public static void analyse(FieldItem item, Type type, Map<String, Type> genericMap){
        if(type == null)
            return;

        ClassMate mate = getClassMate(type, genericMap);

        if(mate != null){
            item.cloneClassMate(mate);
        }
    }

    /**
     * 解析type to classMate
     * @param type
     * @param genericMap
     * @return
     */
    private static ClassMate getClassMate(Type type, Map<String, Type> genericMap) {
        String typeFullName = type.toString();

        ClassMate mate = DocletContext.getClassMate(typeFullName);
        if(mate != null){
            return mate;
        }

        ClassDoc classDoc = null;

        if(typeFullName.length() < 3 && genericMap != null && genericMap.containsKey(typeFullName)){
            //泛型
            Type genericType = genericMap.get(typeFullName);
            typeFullName = genericType.toString();

            mate = DocletContext.getClassMate(typeFullName);
            if(mate != null){
                return mate;
            }

            classDoc = genericType.asClassDoc();
        }else{
            classDoc = type.asClassDoc();
        }

        //如果不存在时,需要重新构建
        mate = new ClassMate(typeFullName);
//        logger.info("正在分析类: {}", typeFullName);
        DocletContext.addClassMate(mate);

        if(classDoc == null){
            //如果是基础数据类型
            mate.setPackageName("");
            mate.setClassName(type.toString());
            return mate;
        }


        //包名和类名
        String classFullName = classDoc.qualifiedTypeName();
        Matcher matcher = CLASS_FULL_NAME_PATTERN.matcher(classFullName);
        if(matcher.find()){
            mate.setPackageName(matcher.group(1));
            mate.setClassName(matcher.group(2));
        }else{
            mate.setPackageName("");
            mate.setClassName(classFullName);
        }

        //文件路径
        mate.setJavaFilePath(classDoc.position().file().getAbsolutePath());

        //泛型处理 保存: T => com.fangdd.nh.doclet.option.UserDetailOutOption
        Map<String, Type> genericMapSelf = new HashMap<String, Type>();
        ParameterizedType parameterizedType = type.asParameterizedType();
        TypeVariable[] typeParameters = classDoc.typeParameters();
        if(parameterizedType != null){
            Type[] typeArguments = parameterizedType.typeArguments();
            if(typeArguments != null && typeArguments.length > 0){
                for (int i = 0; i < typeArguments.length; i++) {
                    Type typeArgument = typeArguments[i];
                    ClassMate argumentType = getClassMate(typeArgument, genericMapSelf);
                    mate.addTypeArgument(argumentType);

                    genericMapSelf.put(typeParameters[i].typeName(), typeArgument);
                }
            }
        }

        //处理属性
        anlyseField(mate, classDoc, genericMapSelf);

        return mate;
    }

    private static void anlyseField(ClassMate mate, ClassDoc classDoc, Map<String, Type> genericMap){
        //获取所有的public方法
        Map<String, Boolean> METHOD_MAPS = ClassDocHelper.getMethods(classDoc);

        //获取所有的属性
        List<FieldDoc> fields = ClassDocHelper.getFields(classDoc);

        if(fields != null && fields.size() > 0){
            for (FieldDoc field : fields){
                String fieldName = field.name();
                String fieldHumpName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                if(!(METHOD_MAPS.containsKey("set" + fieldHumpName) && METHOD_MAPS.containsKey("get" + fieldHumpName))){
                    continue;
                }

                //解析属性
//                logger.info("分析属性: {}.{}", mate.getFullName(), fieldName);
                FieldAnalyser.analyse(mate, field, genericMap);
            }
        }
    }
}
