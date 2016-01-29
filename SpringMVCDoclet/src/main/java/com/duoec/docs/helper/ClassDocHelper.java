package com.duoec.docs.helper;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MethodDoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ycoe on 16/1/28.
 */
public class ClassDocHelper {
    public static List<FieldDoc> getFields(ClassDoc classDoc) {
        List<FieldDoc> fs = new ArrayList<FieldDoc>();
        FieldDoc[] fields = classDoc.fields(false);
        for (int i = 0; i < fields.length; i++) {
            FieldDoc f = fields[i];
            if(!TagHelper.contendTag(f.tags(), "@exclude")){
                fs.add(f);
            }
        }
        ClassDoc superClassDoc = classDoc.superclass();
        if(superClassDoc != null){
            List<FieldDoc> superClassFields = getFields(superClassDoc);
            if(superClassFields != null && superClassFields.size() > 0){
                for (int i = 0; i < superClassFields.size(); i++) {
                    if(!TagHelper.contendTag(superClassFields.get(i).tags(), "@exclude")){
                        fs.add(superClassFields.get(i));
                    }
                }
                return fs;
            }
        }
        return fs;
    }

    public static Map<String, Boolean> getMethods(ClassDoc classDoc) {
        Map<String, Boolean> methodMap = new HashMap<String, Boolean>();
        MethodDoc[] methods = classDoc.methods();
        if(methods != null && methods.length > 0){
            for(MethodDoc method : methods){
                if(!method.isPublic()){
                    continue;
                }
                methodMap.put(method.name(), true);
            }
        }
        ClassDoc superClassDoc = classDoc.superclass();
        if(superClassDoc != null && !superClassDoc.qualifiedName().equals("java.lang.Object")){
            Map<String, Boolean> superMethodMap = getMethods(superClassDoc);
            if(superMethodMap != null && superMethodMap.size() > 0){
                for (String methodName : superMethodMap.keySet()) {
                    methodMap.put(methodName, true);
                }
            }
        }
        return methodMap;
    }
}
