package com.duoec.docs;

import com.duoec.doclet.helper.StringHelper;
import com.duoec.docs.analyse.ControllerAnalyse;
import com.duoec.docs.constant.DocletContext;
import com.duoec.docs.constant.SpringMvcDocletConstant;
import com.duoec.docs.doclet.IDoclet;
import com.duoec.docs.helper.SystemHelper;
import com.duoec.docs.logger.Logger;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.Doclet;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;

import java.util.List;

/**
 * Created by ycoe on 15-11-27.
 */
public class SpringMVCDoclet extends Doclet {
    private static final Logger logger = Logger.getInstance(SpringMVCDoclet.class);

    public static boolean start(RootDoc root) {
        boolean result = create(root);
        Logger.shutdown();
        return result;
    }

    private static boolean create(RootDoc root) {
        String projectPath = SystemHelper.getProjectPath();

        //初始化客户参数
        SystemHelper.initCustomerParams();

        //输出路径
        SpringMvcDocletConstant.OUTPUT_PATH = projectPath;
        logger.info("文档输出目录:" + SpringMvcDocletConstant.OUTPUT_PATH);

        String docletName = SystemHelper.getDocletClass();
        if (StringHelper.isEmpty(docletName)) {
            logger.error("IDoclet未指定!");
            return false;
        }

        logger.info("使用IDoclet=>{}", docletName);

        IDoclet doclet = null;
        try {
            doclet = (IDoclet) Class.forName(docletName).newInstance();
        } catch (InstantiationException e) {
            logger.error(e, "实例化{}出错,InstantiationException!", docletName);
            return true;
        } catch (IllegalAccessException e) {
            logger.error(e, "实例化{}出错,IllegalAccessException!", docletName);
            return true;
        } catch (ClassNotFoundException e) {
            logger.error(e, "实例化{}出错,ClassNotFoundException!", docletName);
            return true;
        }

        //解析Controller
        ClassDoc[] classes = root.classes();
        for (ClassDoc classDoc : classes) {
            //解析
            ControllerAnalyse.analyse(classDoc);
        }

        List<String> bookNames = DocletContext.getBookNames();
        if (doclet != null && !bookNames.isEmpty()) {
            for (String bookName : bookNames) {
                doclet.createDocs(DocletContext.getBook(bookName));
            }
        }
        return true;
    }

    public static LanguageVersion languageVersion() {
        return LanguageVersion.JAVA_1_5;
    }
}
