package com.duoec.docs.analyse;

import com.duoec.doclet.constant.DocletConstant;
import com.duoec.doclet.dto.Book;
import com.duoec.doclet.dto.BookSection;
import com.duoec.doclet.helper.StringHelper;
import com.duoec.docs.constant.SpringMvcDocletConstant;
import com.duoec.docs.constant.DocletContext;
import com.duoec.docs.helper.AnnotationHelper;
import com.duoec.docs.helper.TagHelper;
import com.duoec.docs.logger.Logger;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Tag;

/**
 * Created by ycoe on 16/1/27.
 */
public class ControllerAnalyse {
    private static final Logger logger = Logger.getInstance(ControllerAnalyse.class);

    private ControllerAnalyse(){}

    public static void analyse(ClassDoc controllerDoc){
        if(!isAvailableController(controllerDoc)){
            return;
        }
        AnnotationDesc requestMappingAnnotation = AnnotationHelper.getAnnotation(controllerDoc.annotations(), SpringMvcDocletConstant.ANNOTATION_REQUEST_MAPPING);
        Tag[] controllerTags = controllerDoc.tags();
        String bookName = TagHelper.getStringValue(controllerTags, "@book", DocletConstant.DEFAULT_BOOK_NAME);
        if(StringHelper.isEmpty(bookName)){
            bookName = DocletConstant.DEFAULT_BOOK_NAME;
        }

        Book book = DocletContext.getBook(bookName);

        String controllerName = controllerDoc.name();
        String url = AnnotationHelper.getStringValue(requestMappingAnnotation, "value");

        String comment = controllerDoc.commentText();
        //获取分类名称
        String sectionName = TagHelper.getStringValue(controllerTags, "@cate", null);
        if(StringHelper.isEmpty(sectionName)){
            //尝试使用注释第一行
            if(StringHelper.isNotEmpty(comment)){
                sectionName = StringHelper.firstLine(comment);
                comment = StringHelper.deleteFirstLine(comment);
            }
        }
        if(StringHelper.isEmpty(sectionName)){
            sectionName = controllerName;
        }

        //分类目录
        String path = TagHelper.getStringValue(controllerTags, "@path", controllerDoc.qualifiedName().replaceAll("\\.", "_"));
        path = book.getPath() + "/" + path;

        BookSection section = book.getSection(sectionName);
        section.setComment(comment);
        section.setPath(path);
        section.setUrl(book.getUrl() + url);

        String rankStr = TagHelper.getStringValue(controllerTags, "@rank", "255");
        if(rankStr.matches("^\\d+$")){
            section.setRank(Integer.parseInt(rankStr));
        }

        MethodDoc[] methods = controllerDoc.methods();
        for(MethodDoc method : methods){
            ApiAnalyser.analyse(method, section);
        }
    }

    /**
     * 检查是否合法的Controller
     * @param classDoc
     * @return
     */
    private static boolean isAvailableController(ClassDoc classDoc){
        String controllerName = classDoc.qualifiedName();
        AnnotationDesc restControllerAnnotation = AnnotationHelper.getAnnotation(classDoc.annotations(), SpringMvcDocletConstant.ANNOTATION_REST_CONTROLLER);
        AnnotationDesc controllerAnnotation = AnnotationHelper.getAnnotation(classDoc.annotations(), SpringMvcDocletConstant.ANNOTATION_CONTROLLER);
        if(restControllerAnnotation == null && controllerAnnotation == null){
            logger.error("ERROR: 类{},未指定@RestController或@Controller", controllerName);
            return false;
        }

        AnnotationDesc requestMappingAnnotation = AnnotationHelper.getAnnotation(classDoc.annotations(), SpringMvcDocletConstant.ANNOTATION_REQUEST_MAPPING);
        if(requestMappingAnnotation == null) {
            logger.error("ERROR: 类{},未指定@RequestMapping", controllerName);
            return false;
        }
        return true;
    }
}
