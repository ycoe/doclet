package com.duoec.docs.dto;

import com.sun.javadoc.AnnotationDesc;

/**
 * Created by ycoe on 16/1/27.
 */
public class RequestFieldItem extends FieldItem{

    /**
     * 请求注解: @PathVariable / @RequestParam / @RequestHeader / @JsonToBean ...
     */
    private AnnotationDesc annotationDesc;

    public AnnotationDesc getAnnotationDesc() {
        return annotationDesc;
    }

    public void setAnnotationDesc(AnnotationDesc annotationDesc) {
        this.annotationDesc = annotationDesc;
    }
}
