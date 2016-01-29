package com.duoec.docs.constant;

/**
 * Created by ycoe on 15-11-28.
 */
public class DocletConstant {

    public static final String ANNOTATION_REST_CONTROLLER = "org.springframework.web.bind.annotation.RestController";

    public static final String ANNOTATION_CONTROLLER = "org.springframework.stereotype.Controller";

    public static final String ANNOTATION_REQUEST_MAPPING = "org.springframework.web.bind.annotation.RequestMapping";

    public static final String ANNOTATION_NOT_NULL = "javax.validation.constraints.NotNull";

    public static final String TURN_LINE_2 = "\n\n";

    public static final String TAB = "   ";

    public static final String TURN_LINE = "\n";

    /**
     * 默认的book name
     */
    public static final String DEFAULT_BOOK_NAME = "docs";

    /**
     * 默认web_app_context,比如/api
     */
    public static String WEB_APP_CONTEXT;

    /**
     * 文档输出目录
     */
    public static String OUTPUT_PATH;

    private DocletConstant(){}

}
