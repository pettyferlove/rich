## 目录

- [项目基本说明](#项目基本说明)
- [开发工具及所需环境](#开发工具及所需环境)
- [启动顺序](#启动顺序)
- [目录结构说明](#目录结构说明)
- [项目开发](#项目开发)
- [注意事项](#注意事项)
- [命名规范](#命名规范)

## 项目基本说明

- 名称

Rich微服务基础架构

- 作用

用于项目微服务项目开发过程支撑，包含注册中心，授权中心，配置中心，监控中心，消息中心，网关等模块
 
- git 地址

https://gitee.com/union_tech_company/rich.git

## 开发工具及所需环境

- 开发工具

1. Idea （推荐）
2. Eclipse

- 所需环境

1. Oracle JDK 1.8或以上
2. Maven 3.6.0或以上
3. Nginx 1.15.1或以上
4. Redis 3.4或以上
5. RabbitMQ 3.8或以上
5. MySQL 8.0或以上
6. Docker For Windows 8.0或以上

- Docker环境配置（针对Windows环境）
1. Docker IP配置 10.0.100.0
2. Windows Route 加入桥接 route -p add 172.17.0.0 MASK 255.255.255.0 10.0.100.2;

## 启动顺序
#### 必须服务
1. Rich Eureka Service 服务注册中心
2. Rich Config Service 服务配置中心
2. Rich Gateway Service 网关服务
3. Rich Auth Service 授权服务
4. Rich Base Info Service 基础信息服务
5. Rich Log Service 用户日志收集服务
6. Rich Message Service 消息发送服务
7. Other业务服务
#### 辅助服务
1. Rich Monitor Service 服务监控

## 目录结构说明

```
rich
├─rich-attachment-service 附件服务
├─rich-auth-service 授权服务（Server端）
├─rich-common 公共依赖
  ├─rich-common-core 公共依赖核心模块
  ├─rich-common-log 公共日志模块
  ├─rich-common-message 消息流公共对象封装
  ├─rich-common-security 公共安全模块
  ├─rich-common-sleuth-zipkin 服务链路最终公共依赖
├─rich-config-service 配置中心服务
├─rich-eureka-service 服务注册中心
├─rich-gateway-service 对外访问网关
├─rich-log-servcie 用户日志收集服务
├─rich-message-service 消息服务
├─rich-module 业务模块父级目录
  ├─rich-base-info-api 基础信息服务Api
  ├─rich-base-info-service 基础信息服务
  ├─rich-code-generator 代码生成器
  ├─rich-demo-servcie Demo服务
  ├─rich-third-party-servcie 第三方互联网平台对接服务
├─rich-monitor-service 监控服务
├─rich-tx-manager-service 分布式事务协调中心服务
├─rich-zipkin-service 服务链路追踪信息聚合服务
```

####分布式事务协调器
Rich Tx Manager Service

## 端口说明
- 公认端口（Well Known Ports）：从0到1023，它们紧密绑定（binding）于一些服务。通常这些端口的通讯明确表明了某种服务的协议。例如：80端口实际上总是HTTP通讯。
- 注册端口（Registered Ports）：从1024到49151。它们松散地绑定于一些服务。也就是说有许多服务绑定于这些端口，这些端口同样用于许多其它目的。例如：许多系统处理动态端口从1024左右开始。
- 动态和/或私有端口（Dynamic and/or Private Ports）：从49152到65535。理论上，不应为服务分配这些端口。实际上，机器通常从1024起分配动态端口。但也有例外：SUN的RPC端口从32768开始。

### 服务端口

端口  | 服务  | 说明
--- | ---  | --- 
10151 | rich-eureka-service | 服务注册中心端口
10152 | rich-config-service | 配置服务端口
10153 | rich-log-service | 日志服务端口
10154 | rich-attachment-service | 附件服务端口
10156 | rich-message-service | 消息服务端口
10157 | rich-workflow-service | 工作流服务端口
10158 | rich-monitor-service | 监控服务端口
20000 | rich-base-info-service | 基础信息服务端口
20001 | rich-third-party-service | 第三方对接服务端口
8020 | rich-gateway-service | 网关服务端口
6060 | rich-auth-service | 授权服务端口
9411 | rich-zipkin-service | 服务端口
7970 | rich-tx-manager-service | 服务端口
8070 | rich-tx-manager-service | 客户端连接端口

### 中间件端口
9020 | kafka | kafka客户端连接端口
5672 | rabbitmq | 客户端连接端口
15672 | rabbitmq | 可视化控制台Http端口

### 数据库端口
3306 | mysql | MYSQL连接端口

## 项目开发
后续项目开发主要在一下模块下开发：
~~~
**建议使用**
<parent>
    <groupId>com.github.rich</groupId>
    <artifactId>rich-module</artifactId>
    <version>${revision}</version>
</parent>

**支撑整个Rich生态的服务使用该依赖**
<parent>
    <groupId>com.github.rich</groupId>
    <artifactId>rich</artifactId>
    <version>${revision}</version>
</parent>
~~~

## 注意事项
由于现阶段没有私服，所以至少需要拉去Rich项目执行一次mvn package，
后续可以另行开启项目并设置好相关依赖即可开始开发

## 命名规范
### Service接口命名
1. Feign远程调用消费者接口必须是 RemoteXxxService 样式，用于区分本地调用与远程调用
2. Feign远程调用生产者必须在服务api包下进行开发，需定义 XxxServiceApi 接口，并实现.用于区分对外访问接口与对内远程调用接口
3. 持久层Service 则必须是 IXxxService 样式
### DTO命名
> 数据传输对象，这个概念来源于J2EE的设计模式，原来的目的是为了EJB的分布式应用提供粗粒度的数据实体，以减少分布式调用的次数，从而提高分布式调用的性能和降低网络负载，但在这里，我泛指用于服务层之间的数据传输对象。
用于服务内部调用过程中的传输对象
1. 对象命名均为 XxxDTO样式
### VO命名
> 视图对象，用于展示层，它的作用是把某个指定页面（或组件）的所有数据封装起来
1. 对象命名均为 XxxVO样式

## 更新日志

序号  | 版本号 | 日期 | 修改人 | 概述
--- | --- | --- | --- | ---
1 | 1.0.0 | 2019-3-21 | Petty | 初版
2 | 1.1.0 | 2019-6-05 | Petty | 基础信息接口完成开发（后端参数验证待处理）
3 | 1.2.0 | 2019-6-27 | Petty | 动态路由接口实现，WebSocket主动推送
4 | 1.3.0 | 2019-9-24 | Petty | FastJson零日漏洞修复升级，数据库修改时间字段命名规范化以及实体类调整
5 | 2.0.0 | 2019-12-02 | Petty | 注册中心&配置中心迁移至Nacos，移除无用代码，统一异常返回
6 | 2.0.1 | 2019-12-06 | Petty | 升级mybatis-plus至3.3.0，druid至1.1.21，移除mybatis-plus配置类逻辑删除插件




