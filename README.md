#Rich
##项目模块

##启动顺序
####必须服务
1. Rich Eureka Service 服务注册中心
2. Rich Config Service 服务配置中心
2. Rich Gateway Service 网关服务
3. Rich Auth Service 授权服务
4. Rich Base Info Service 基础信息服务
5. Other业务服务
####辅助服务
1. Rich Message Service 消息发送服务
2. Rich Monitor Service 服务监控

####分布式事务协调器
Rich Tx Manager Service

##项目开发
后续项目开发主要依赖于：
~~~
**建议使用**
<parent>
    <groupId>com.github.rich</groupId>
    <artifactId>rich-module</artifactId>
    <version>1.0.0-RELEASE</version>
</parent>

**支撑整个Rich生态的服务使用该依赖**
<parent>
    <groupId>com.github.rich</groupId>
    <artifactId>rich</artifactId>
    <version>1.0.0-RELEASE</version>
</parent>
~~~

##注意事项
由于现阶段没有私服，所以至少需要拉去Rich项目执行一次mvn package，
后续可以另行开启项目并设置好相关依赖即可开始开发




