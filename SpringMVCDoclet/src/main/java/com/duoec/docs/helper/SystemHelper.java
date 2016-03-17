package com.duoec.docs.helper;

import com.duoec.doclet.helper.StringHelper;
import com.duoec.docs.constant.SpringMvcDocletConstant;

/**
 * Created by ycoe on 16/1/27.
 */
public class SystemHelper {
    private SystemHelper(){}

    /**
     * 获取项目目录
     * @return
     */
    public static String getProjectPath() {
        return System.getProperty("user.dir");
    }

    /**
     * 初始化客户参数
     */
    public static void initCustomerParams() {
        SpringMvcDocletConstant.WEB_APP_CONTEXT = StringHelper.isNullThenSet(System.getProperty("web.app.context"), "");


    }

    public static String getDocletClass() {
        return StringHelper.isNullThenSet(System.getProperty("customer.doclet"), "com.duoec.docs.doclet.gitbook.GitBookDocletImpl");
    }
}
