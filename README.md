## 目录

- [项目基本说明](#项目基本说明)
- [开发工具及所需环境](#开发工具及所需环境)
- [启动顺序](#启动顺序)
- [目录结构说明](#目录结构说明)
- [项目开发](#项目开发)
- [注意事项](#注意事项)

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

## 目录结构说明

```
rich
├─rich-attachment-service 附件服务
├─rich-auth-service 授权服务（Server端）
├─rich-common
  ├─rich-common-core 公共依赖核心模块
  ├─rich-common-log 公共日志模块
  ├─rich-common-security 公共安全模块
├─rich-config-service 配置中心服务
├─rich-eureka-service 服务注册中心
├─rich-gateway-service 对外访问网关
├─rich-message-service 消息服务
├─rich-module 业务模块父级目录
  ├─rich-base-info-api 基础信息服务Api
  ├─rich-base-info-service 基础信息服务
  ├─rich-code-generator 代码生成器
  ├─rich-demo-servcie Demo服务
  ├─rich-third-party-servcie 第三方互联网平台对接服务
├─rich-monitor-service 监控服务
├─rich-tx-manager-service 分布式事务协调中心服务
```

####分布式事务协调器
Rich Tx Manager Service

## 端口说明

端口  | 服务  | 说明
--- | ---  | --- 
10151 | rich-eureka-service | 服务端口
10152 | rich-config-service | 服务端口
7970 | rich-tx-manager-service | 服务端口
8070 | rich-tx-manager-service | 客户端连接端口

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

## 更新日志

序号  | 版本号 | 日期 | 修改人 | 概述
--- | --- | --- | --- | ---
1 | 1.0.0 | 2019-3-21 | Petty | 初版
2 | 1.1.0 | 2019-6-05 | Petty | 基础信息接口完成开发（后端参数验证待处理）




