package com.duoec.docs.doclet.gitbook;

import com.duoec.docs.constant.DocletConstant;
import com.duoec.docs.doclet.TableFormater;
import com.duoec.docs.dto.RequestFieldItem;
import com.duoec.docs.dto.ResponseFieldItem;
import com.duoec.docs.helper.StringHelper;
import com.duoec.docs.doclet.JsonFormater;
import com.duoec.docs.dto.ApiItem;

import java.util.List;

/**
 * Created by ycoe on 16/1/28.
 */
public class ApiMD {
    public static String getApiSummaryString(ApiItem api) {
        StringBuilder sb = new StringBuilder();
        sb.append(DocletConstant.TAB);
        sb.append("* [");
        sb.append(api.getName());
        sb.append("](");
        String path = api.getSection().getPath() + "/" + api.getFileName() + ".md";
        sb.append(path);
        sb.append(")");
        sb.append(DocletConstant.TURN_LINE);
        return sb.toString();
    }

    public static String getApiString(ApiItem api) {
        StringBuilder sb = new StringBuilder();
        sb.append("## ");
        sb.append(api.getName());
        sb.append(DocletConstant.TURN_LINE_2);

        //@since
        if(StringHelper.isNotEmpty(api.getSince())){
            sb.append("> @since " + api.getSince());
            sb.append(DocletConstant.TURN_LINE_2);
        }
        //@author
        if(StringHelper.isNotEmpty(api.getAuthor())){
            sb.append("> @author " + api.getAuthor());
            sb.append(DocletConstant.TURN_LINE_2);
        }
        //@deprecated
        if(api.getDeprecated() != null){
            sb.append("> @deprecated " + api.getDeprecated());
            sb.append(DocletConstant.TURN_LINE_2);
        }

        //说明
        String comment = api.getComment();
        if(StringHelper.isNotEmpty(comment)){
            sb.append(comment);
            sb.append(DocletConstant.TURN_LINE_2);
        }

        //接口
        sb.append("### 接口：`");
        sb.append(api.getUrl());
        sb.append("`");
        sb.append(DocletConstant.TURN_LINE_2);

        //Method
        sb.append("### 方法：`");
        sb.append(api.getMethod());
        sb.append("`");
        sb.append(DocletConstant.TURN_LINE_2);

        //请求参数
        List<RequestFieldItem> requestParams = api.getRequestParams();
        if(requestParams == null){
            sb.append("### 接口参数：");
            sb.append("无");
        }else{
            sb.append(TableFormater.toMarkdown(requestParams));
        }
        sb.append(DocletConstant.TURN_LINE_2);

        //响应参数
        ResponseFieldItem response = api.getResponse();
        sb.append("### 正常响应：");
        if(response == null){
            sb.append("无");
        }else{
            sb.append(DocletConstant.TURN_LINE_2);

            String responseComment = response.getComment();
            if(StringHelper.isNotEmpty(responseComment)){
                sb.append(responseComment);
                sb.append(DocletConstant.TURN_LINE_2);
            }
            sb.append("```JSON");
            sb.append(DocletConstant.TURN_LINE);
            sb.append(JsonFormater.toJson(response));
            sb.append(DocletConstant.TURN_LINE);
            sb.append("```");
        }
        sb.append(DocletConstant.TURN_LINE_2);

        //尝试显示依赖的java文件
//        Map<String, List<File>> srcs = DocletConstant.CURRENT_DOC.getSrcs();
//        String api = getCate().getUrl() + getUrl();
//        if(srcs != null && srcs.containsKey(api)){
//            sb.append("### 依赖java文件 (请下载后重命名)");
//            sb.append(DocletConstant.TURN_LINE_2);
//            List<File> javas = srcs.get(api);
//            for(File java : javas){
//                sb.append(FileHelper.getJavaSrc(java, 2));
//            }
//        }

        return sb.toString();
    }
}
