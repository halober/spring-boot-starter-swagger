# spring-boot-starter-swagger
该项目是spring-boot与`swagger`的整合，[swagger](https://swagger.io/)是一款高效易用的嵌入式文档插件。引入该项目，可以让你的api发布更容易，修改发布更快捷，团队交流更高效。

## 本项目特点
1.通过spring-boot方式配置的swagger实现，完美并且完整的支持swagger-spring的配置项

2.配置及其简单，容易上手。

2.支持api分组配置，通过正则表达式方式分组

3.支持分环境配置，你可以很容易让你的项目api文档在开发环境，测试环境，预发布环境查看，而在生产环境不可查看

## 快速入门
快速入门[点击这里](http://blog.csdn.net/hulei19900322/article/details/78107516)

## 生成客户端代码
生成客户端代码[点击这里](http://blog.csdn.net/hulei19900322/article/details/78107874)

### 项目推荐
小项目写多了，你或许需要开始考虑分布式式，考虑rpc框架了，dubbo一定是你最好的选择，这个项目则是你使用dubbo最优的入口 [spring-boot-starter-dubbo](https://gitee.com/reger/spring-boot-starter-dubbo)

### 更新记录
```
1.0.2
  发布时间：2017年10月15日
  更新内容：
    1.调整配置 spring.swagger-group到spring.swagger.group
    2.增加显示启用swagger配置的参数spring.swagger.enabled
  注： 本次升级完全兼容1.0.1
1.0.1
  发布时间：2017年9月17日 
  更新内容：
    1. 解决1.0.0发版中的bug
1.0.0
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
