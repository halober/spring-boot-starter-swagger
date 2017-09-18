# spring-boot-starter-swagger
该项目是spring-boot与`swagger`的整合，[swagger](https://swagger.io/)是一款高效易用的嵌入式文档插件。引入该项目，可以让你的api发布更容易，修改发布更快捷，团队交流更高效。

## 本项目特点
1.通过spring-boot方式配置的swagger实现，完美并且完整的支持swagger-spring的配置项

2.配置及其简单，容易上手。

2.支持api分组配置，通过正则表达式方式分组

3.支持分环境配置，你可以很容易让你的项目api文档在开发环境，测试环境，预发布环境查看，而在生产环境不可查看


## 快速入门
#### 1.在maven管理的spring-boot项目中引入依赖,（建议使用spring-boot版本1.4以上,1.4以下未测试过）
```xml
    <dependency>
        <groupId>com.gitee.reger</groupId>
        <artifactId>spring-boot-starter-swagger</artifactId>
        <version>${spring-boot-starter-swagger.version}</version>
    </dependency>
```
#### 2.在spring-boot项目增加配置文件'application-api.yml',在其中配置swagger的信息，如下
```yml
spring:
  swagger-group:
      user-api:                                     # 用户组api，可以配置多个组
        group-name: 01.user-api                     # api组的名字，会在swagger-ui的api下拉列表中显示；组名前的序号，多个组可以排序；最好不要写中文
        title: 用户相关的操作                        # api组的标题，会在swagger-ui的标题处显示
        description: 用户相关的操作，包括用户登录登出  # api组的描述，会在swagger-ui的描述中显示
        path-regex: /api/user/.*                    # 匹配到本组的api接口，匹配uri，可以用用正则表达式
        path-mapping: /                             # 匹配到的url在swagger中测试请求时加的url前缀
        version: 0.0.0                              # api版本
        license: 该文档仅限公司内部传阅               # 授权协议
        license-url: '#'                            # 授权协议地址
        terms-of-service-url:                       # 服务条款地址
        contact:                                    # 文档联系人
          name: 张三                                # 联系人名字
          email: zhangsan@team.com                  # 联系人邮箱
          url: http://www.team.com                  # 联系地址
```
以上是swagger的配置，其中组可以配置多个，组名以数字打头，多个组可以排序

#### 3.启用swagger配置
启用swagger配置，使用spring-boot的Profile方式，对应的Profile参数是api。所以分环境启用，只需要在对应环境的配置文件的profiles的include中包含api参数。例如要在dev环境要启用api，只需要如下在'application-dev.yml'中增加配置
```yml
spring:
  profiles:
    include:
      - api               # 开发环境启用api文档
```
在prod环境不要启用api，只需要'application-prod.yml'中不要在profiles下include和active中包含'api'关键字。

#### 4.配置java代码的文档注解
①. model类中增加配置注解
model中常用的注解：@ApiModel 注解类名，@ApiModelProperty 注解方法或者参数名， 

例如
```java
@ApiModel("输出用户数据的类")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(required=true,value="登录的用户名")
	private String userName;
	@ApiModelProperty(required=true,value="登录的密码")
	private String password;
       
        get/set方法省略，自己脑补吧！
}

```
②. 控制器中增加配置注解

控制器中常用的注解： @Api 注解控制器显示的标识，有tags和description可以配置控制器显示的标识;@ApiOperation 用来注解控制器方法显示的标识； @ApiParam 用来注解控制器方法参数的名字，控制器方法在文档中需要显示的默认值

例如
```java
@Api(tags = "操作用户的api")
@RestController
@RequestMapping("/api/user")
public class UserController {

	@ApiOperation("用户登录")
	@GetMapping("/login")
	public ResponseEntity<User> login(
                        @ApiParam("用户名") @RequestParam String userName,
			@ApiParam("密码") @RequestParam String password) {
            方法体省略，自己脑补吧！
	} 
}
```
③.其它一些注解

swagger的注解主要在包‘io.swagger.annotations’下，除了以上描述的注解外，还有@ApiImplicitParam,@ApiImplicitParams,@ApiKeyAuthDefinition,@ApiResponse,@ApiResponses,@Authorization,@AuthorizationScope,@BasicAuthDefinition,@Contact,@Example,@ExampleProperty,@Extension,@ExtensionProperty,@ExternalDocs,@Info,@License,@OAuth2Definition,@ResponseHeader,@Scope,@SecurityDefinition,@SwaggerDefinition,@Tag

除了swagger自己定义的注解外，还有部分校验注解,注解所在包‘javax.validation.constraints’下，像@Max，@Min,@Size这些也是可用的。

由于这些注解，我觉得swagger中可有可无，就不写描述了。


#### 5.查看文档
启动dev环境
```cmd
java -jar swagger-example.jar --spring.profiles.active=dev --server.port=8080
```
浏览器访问，http://{服务地址}:8080/swagger-ui.html，如果你启动在本机可以直接[点击这里](http://127.0.0.1:8080/swagger-ui.html)

浏览器便可以展示出swagger的html页面


## 生成客户端代码
生成客户端代码，是swagger官方提供的功能。在时间充足的情况下，最好还是自己写，必定自己写的代码更具有持续维护性。但是如果时间有限的情况下，使用它开发客户端调用程序，也不失是一种有效解决办法。
使用方法

①. 下载文档的描述json

下载地址，http://{服务启动主机地址}:{服务启动端口}/v2/api-docs?group={需要生成的api所属组name}，如果需要下上边‘01.user-api’组的的json文档地址,并且服务启动在本地8080端口上，可以[点击这里](http://127.0.0.1:8080/v2/api-docs?group=01.user-api)下载。

② 生成客户端代码

打开[swagger-editor](https://editor.swagger.io/),点击页面左上角，‘file’->importFile->选择刚才下载的json文件上传-> 点击‘Generate Client’->‘点选你要生成的客户端语言’->等一下，会自动下载生成的客户端程序包

### 项目推荐
小项目写多了，你或许需要开始考虑分布式式，考虑rpc框架了，dubbo一定是你最好的选择，这个项目则是你使用dubbo最优的入口 [spring-boot-starter-dubbo](https://gitee.com/reger/spring-boot-starter-dubbo)

### 更新记录
```
1.0.1
  发布时间：2017年9月17日 
  更新内容：
    1. 解决1.0.1发版中的bug
1.0.2
  发布时间：2017年9月16日
  更新内容：
    1. 完成基础功能

```
## 示例项目
#### 1.克隆[示例代码](https://gitee.com/lei0719/spring-boot-starter-swagger-example)
```cmd
git clone git@gitee.com:lei0719/spring-boot-starter-swagger-example.git
```
#### 2.启动服务
进入示例代码的目录执行命令
```cmd
mvn spring-boot:run -Dspring.profiles.active=dev  -Dserver.port=8080
```
#### 3.查看文档
访问地址[点击这里](http://127.0.0.1:8080/swagger-ui.html)
