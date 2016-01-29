package com.duoec.test.controller;

import com.duoec.test.option.HttpResult;
import com.duoec.test.option.UserInfoInOption;
import com.duoec.test.option.UserDetailOutOption;
import com.duoec.test.option.UserOutOption;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 用户接口
 * 测试接口
 * @author 徐文振
 * @date 15/9/2
 * @rank 1
 * @book user-app
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    /**
     * 获取用户基本信息1
     * 接口说明1
     * @since 1.1
     * @author 徐文振
     * @param option 请求
     * @return 测试return描述
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public HttpResult<UserDetailOutOption> get(@PathVariable int apiVersion, @RequestBody List<UserInfoInOption> option){
        UserOutOption user = new UserOutOption();
        return HttpResult.successWithData(null);
    }
}
