# spring-boot-starter-swagger
该项目是spring-boot与`swagger`的整合，[swagger](https://swagger.io/)是一款高效易用的嵌入式文档插件。引入该项目，可以让你的api发布更容易，修改发布更快捷，团队交流更高效。

## 本项目特点
1.通过spring-boot方式配置的swagger实现，完美并且完整的支持swagger-spring的配置项

2.配置及其简单，容易上手。

2.支持api分组配置，通过正则表达式方式分组

3.支持分环境配置，你可以很容易让你的项目api文档在开发环境，测试环境，预发布环境查看，而在生产环境不显示文档


### 快速入门
1.在maven管理的spring-boot项目中引入依赖,（建议使用spring-boot版本1.4以上,1.4以下未测试过）
```xml
    <dependency>
        <groupId>com.gitee.reger</groupId>
        <artifactId>spring-boot-starter-swagger</artifactId>
        <version>${spring-boot-starter-swagger.version}</version>
    </dependency>
```
2.在spring-boot项目的增加配置文件'application-api.yml',在其中配置swagger的信息，如下
```yml
spring:
  swagger-group:
      user-api:                                     # 用户组api，可以配置多个组
        group-name: 01.user-api                     # api组的名字，会在swagger-ui的api下拉列表中显示,组名前的序号，多个组可以排序，最好不要写中文
        title: 用户相关的操作                        # api组的标题，会在swagger-ui的标题处描述
        description: 用户相关的操作，包括用户登录登出  # api组的描述，会在swagger-ui的描述中显示
        path-regex: /api/user/.*                    # 匹配到本组的api接口，匹配uri，可以用用正则表达式
        path-mapping: /                             # 匹配到的url在swagger中测试请求时加的url前缀
        version: 0.0.0                              # api版本
        license: 该文档仅限公司内部传阅               # 授权协议
        license-url: '#'                            # 授权协议地址
        terms-of-service-url:                      # 服务条款地址
        contact:                                    # 文档联系人
          name: 张三                                # 联系人名字
          email: zhangsan@team.com                  # 联系人邮箱
          url: http://www.team.com                  # 联系地址
```
以上是swagger的配置，其中组可以配置多个，组名以数字打头，多个组可以排序

# [示例项目](https://gitee.com/reger/spring-boot-starter-swagger-example)
