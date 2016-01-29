# SpringMVCDoclet 使用文档

SpringMVCDoclet根据Spring MVC框架，生成GitBook规范生成MarkDown文档。

项目依赖：tools.jar (JDK自带包)

用于生成文档的注释分为：Controller类注释、Controller方法注释、入参JavaBean注释和响应JavaBean注释

每个接口文档分为：
接口标题、接口定义、接口参数、接口响应、依赖的Java文件五部分

以：[楼盘详情（第一页）](http://doc.duoec.com/docs/HouseController/getDetail.html)为例：

对应的Controller为：`com.duoec.test.controller.HouseController`

## 一、接口标题

“楼盘详情（第一页）” 此接口的定义方法：

`com.duoec.test.controller.HouseController#getDetail`

有三种方式：

1. 添加接口注释tag
	`@title 楼盘详情（第一页）`
	
2. 如果`@title`不存在，则尝试使用将接口注释的第一行作为接口名称

3. 如果注释也不存在，则使用方法名`getDetail`作为接口名称



## 二、接口定义

接口定义有两部分：url和method

接口：`/api/house/{id}`

方法：`GET`

接口地址由三部分组成：

1. 首先会从pom.xml配置中读取插件的信息：

	```
	<additionalJOptions>
	     <additionalJOption>-J-Dproject.path=${basedir}</additionalJOption>
	</additionalJOptions>
	```

	即`project.path=${basedir}`，其中`${basedir}`为maven的变量，表示当前项目路径

	GitBookDoclet会尝试从`${basedir}/src/main/resources/server.properties`文件中读取

	`jetty.http.context`的内容，即`/api`

2. 再从HouseController的类注解里读取`@RequestMapping(value = "/house")`

3. 最后从方法注解中读取`@RequestMapping(value = "/{id}", method = RequestMethod.GET)`

由这三部分内容串起来，即是接口地址：`/api/house/{id}`

接口方法，直接从Controller类的方法中读取注解`@RequestMapping(value = "/{id}", method = RequestMethod.GET)`，如果注解属性`method`不存在，则默认为`GET`

<span style="color:red">注意：当前不区分方法多态，所以同一个Controller中不要存在多态的接口方法！不然后面的接口会覆盖前面的！</span>

## 三、接口参数

接口参数分为：请求头、接口参数


### 1.参数来源

接口参数，即接口方法的参数！接受注解：`@RequestHeader` / `@PathVariable` / `@RequestParam` 

其中，从其它地方注入进来的，未在接口方法里定义的参数，将不列入接口文档！
	
注解为`@RequestBody`的方法参数，将列入接口参数，如果为标准的pojo，也会在后面单独将此参数的属性列出来，见：[领取抵用券](http://doc.duoec.com/CouponController/obtainRedPacketCode.html)	


### 2.参数说明

从`@RequestHeader` / `@PathVariable` / `@RequestParam`进来的参数，参数说明需要写在方法参数的注释里面：`@param id 楼盘ID`
	
如果变量是从pojo类里面进来的，参数说明需要写在pojo的属性里面，如：
	
```
public class HouseDetailOutOption implements Serializable{
    /**
	 * 楼盘ID
     * @demo 1234567
	 */
    private long houseId;
	    	    
    //其它属性和getter/setter 
}
```
	
在接口参数里面，注释的tag（比如`@demo`）没有作用，在响应参数里面非常重要，将在后面详细介绍


### 3.参数是否必填

默认所有参数都是必填的！

如果非必填，可以在`@RequestParam(value = "id", required = false) Long id`, 指定required为false

pojo的属性，默认都是非必填的，可以有两种方法指定必填：

	a. 添加属性注解`@NotNull`

	b. 在属性注释中添加tag`@request false`
	

## 四、正常响应

文档暂时未把异常响应包含进来

正常响应数据以json结构数据展示包含了：响应实体类型、字段名称、demo值和注释四部分

### 1. 响应实体类型

test-mvc为例，所有的响应实体都是HttpResult<T>类型。比如：

`HttpResult<com.duoec.test.option.output.house.HouseDetailOutOption>`

为实现代码的可读，请在写代码时，务必写明泛型类型。

比如错误的写法：

`public HttpResult<?> getDetail(@PathVariable("id") long id){｝`

正确的写法：

`public HttpResult<HouseDetailOutOption> getDetail(@PathVariable("id") long id){}`

如果泛型为`java.util`或`java.lang`时，会自动省略包名，比如：

`HttpResult<String>`


### 2. 字段名称

可以解析到文档里的字段名称，都必须同时存在标准的`setter`和`getter`，下面`User`类，fullName没有getter和setter，所以生成文档时不会把此属性包含进来！

```
class User{
  /**
   * 用户名称
   * @demo 张三
   */
  private String name;
  
  /**
   * 全名
   * @demo 张三丰
   */
  private String fullName;
  
  public String getName(){
    return this.name;
  }
  
  public void setName(String name){
    this.name = name;
  }
}
```

会生成的文档如下：

```
{
    "name" : "张三" //用户名称 @type String
}
```


### 3. demo值

其中 `“张三”`即是demo值，此值直接从pojo中的属性标签`@demo`中获取，如果不存在@demo时，显示null，即

```
{
    "name" : null //用户名称 @type String
}
```

如果属性是其它的JavaBean时，值会嵌套另一个json对象，比如以下实体：

```
class Manager{
  /**
   * 用户信息
   */
  private User user;
  
  /**
   * 用户角色 
   * @demo ["user", "admin"]
   */
  private List<String> rules;
  
  //其它getter和setter
}
```
会生成文档：
```
{
  "user" : { // 用户信息 @type com.duoec.test.User
    "name" : "张三" //用户名称 @type String
  },
  "rules" : ["user", "admin"] // 用户角色 @type List<String>
}
```

有三种情况：
> a. 当属性是基础数据类型时，直接使用@demo设置值

> b. 当属性是JavaBean时，会自动嵌套JavaBean内的属性

> c. 当是List/Array/Map类型时，会自动嵌套它的声明的泛型JavaBean内的属性

这里需要特别声明一下，默认，JavaBean必须放在`com.duoec`包下，否则需要通过配置去改变包名：

```
<plugin>
  ...
  <configuration>
    <additionalJOptions>
      <additionalJOption>-J-Dcustomer.package=com.duoec</additionalJOption>
    </additionalJOptions>
  </configuration>
</plugin>
```

如果JavaBean属性是Array/List/Map型时，请显式声明泛型的类型，不用使用?

尽量不要使用Map型，因为它生成的文档好太好指定key的值! SpringMVCDoclet目前对它的支持比较弱

尽量不要使用布尔值、枚举（暂时未测试过...）

建议使用：String / List / Array / 数值型


### 4. 注释

注释比较简单，直接在JavaBean的属性上面写注释即可，可以是行注释或块注释

另外，注释支持特殊的几个tag，比如：

`@since` / `@deprecated`

`@since` 用来标识此属性从哪个版本开始支持

`@deprecated` 用于标识弃用字段，可以带一段说明


## 五、依赖Java文件

本附件是为Android或其它Java客户端服务的，提供接口使用到的入参、响应实体类的下载。注意：

1. GitBookDoclet未对实体类进行特殊处理，直接提供下载，所以尽量不要在此JavaBean中写多余的东西

2. 为防止重名，对依赖的类文件进行了重命名，下载后需要将包名去掉。比如类`com.duoec.test.option.output.house.HouseDetailOutOption`会下载成`com_duoec_test_option_output_house_HouseDetailOutOption.java`文件

## 六、使用

直接在pom.xml内添加插件：

```
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-javadoc-plugin</artifactId>
  <version>2.10.3</version>
  <configuration>
      <doclet>com.duoec.docs.SpringMVCDoclet</doclet>
      <docletArtifact>
        <groupId>com.duoec</groupId>
        <artifactId>SpringMVCDoclet</artifactId>
        <version>1.0.0-SNAPSHOT</version>
      </docletArtifact>
      <sourceFileIncludes>
          <!-- 接口的路径，可以添加多个，当前只是添加NCC-APP的 -->
          <include>com/duoec/test/controller/*.java</include>
      </sourceFileIncludes>
      <sourcepath>
          <!-- 指定源码路径，如果多个模块，需要包含进去。分隔符：linux使用:   windows使用; -->
          ${project.basedir}/src/main/java:${project.basedir}/../ncc-service-remote/src/main/java::/Users/ycoe/Projects/fdd/nh-common/nh-common-restful/src/main/java
      </sourcepath>
      <useStandardDocletOptions>false</useStandardDocletOptions>
      <encoding>UTF-8</encoding>
      <docencoding>UTF-8</docencoding>

      <additionalJOptions>
          <additionalJOption>-J-Dproject.path=${basedir}</additionalJOption>
      </additionalJOptions>
  </configuration>
  <executions>
      <execution>
          <id>attach-javadocs</id>
          <phase>compile</phase>
          <goals>
              <goal>javadoc</goal>
          </goals>
      </execution>
  </executions>
</plugin>
```

## 七、已知问题

1. 在Windows下貌似有问题，貌似是路径的问题，暂时没有去调试它。谁有空可以去看看
  GitLab地址：

2. 支持的类型测试不充分。未测试过布尔值、枚举、其它Collections、Arrays...

3. 不太确定当前的Doclet的接口是否使用正确。[官方文档](https://maven.apache.org/plugins/maven-javadoc-plugin/javadoc-mojo.html)

4. 本Doclet为当前新房C端后台使用的框架编写，未测试过其它的项目，可能存在不适应情况




>   