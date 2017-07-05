## 获取用户基本信息1

> @since 1.1

> @author 徐文振

接口说明1

### 接口：`/user/info`

### 方法：`GET`

|参数名|类型|来源|是否必填|说明|
|:--|:--:|:--:|:--:|:--|
|apiVersion|int|@PathVariable|false||
|option|List<com.duoec.test.option.UserInfoInOption>|@RequestBody|false|请求|


option参数 @type `com.duoec.test.option.UserInfoInOption` 属性

```JSON
{ // @type com.duoec.test.option.UserInfoInOption
   "userId" : 12 //用户ID @type int
}
```

### 正常响应：

测试return描述

```JSON
{ //测试return描述 @type com.duoec.test.option.HttpResult<com.duoec.test.option.UserDetailOutOption>
   "code" : 200, //正常响应值 @type int
   "msg" : "用户未登录", //信息,一般在错误时包含 @type String
   "data" :    { //响应数据 @type com.duoec.test.option.UserDetailOutOption
      "fullName" : "张三李四", //全名 @since 1.7 @deprecated v1.9 @type String
      "staff" : null, //下属 @type List<com.duoec.test.option.UserDetailOutOption>
      "id" : 12, //用户ID @since 1.7 @deprecated v1.9 @type int
      "name" : "张三", //用户名称 @type String
      "tags" : null //标签 @type List<com.duoec.test.option.TagOption>
   }
}
```

