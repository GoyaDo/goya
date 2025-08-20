# Goya - 企业级微服务框架

## 1. 项目简介

`Goya` 是一个基于 **Spring Boot 3.5.3** 和 **Spring Cloud 2025.0.0** 构建的企业级微服务框架。它旨在提供一套统一的、可复用的解决方案，帮助快速开发和部署微服务应用。
- 本项目最新代码仓库地址: https://codeup.aliyun.com/62f27d11f9861067e4e7831d/goya/goya.git

### 核心特性

- **🔐 安全认证**: 基于 Spring Authorization Server 的完整认证授权体系，支持 JWT 和 Opaque Token 双令牌策略
- **🏗️ 微服务架构**: 完整的微服务生态，支持服务注册发现、配置中心、熔断降级、链路追踪
- **🏢 多租户支持**: 提供独立数据库、共享数据库独立Schema、共享数据库共享Schema三种多租户模式
- **📊 数据存储**: 支持 MySQL、PostgreSQL、SQLite、Redis、MongoDB 等多种数据存储方案
- **🚀 高性能缓存**: 集成 Redisson、JetCache 等高性能缓存框架
- **📱 第三方集成**: 支持微信、短信、第三方登录等常用服务集成
- **🔍 监控运维**: 集成 SkyWalking、Grafana 等监控和可视化工具
- **📝 API文档**: 基于 OpenAPI 3.0 的自动文档生成

## 2. 技术栈

### 核心框架
- **Spring Boot**: 3.5.3
- **Spring Cloud**: 2025.0.0
- **Spring Cloud Alibaba**: 2023.0.3.3
- **Java**: 21
- **Maven**: 3.x

### 数据存储
- **关系型数据库**: MySQL, PostgreSQL, SQLite
- **缓存**: Redis (Redisson 3.50.0)
- **文档数据库**: MongoDB
- **ORM框架**: Spring Data JPA, MyBatis-Plus
- **连接池**: HikariCP

### 微服务组件
- **服务注册与发现**: Nacos 3.0.1
- **配置中心**: Nacos Config
- **熔断降级**: Sentinel 1.8.8
- **链路追踪**: SkyWalking 9.4.0
- **消息队列**: Kafka
- **分布式锁**: Redisson

### 安全框架
- **认证授权**: Spring Authorization Server
- **资源服务器**: Spring Security OAuth2 Resource Server
- **JWT**: Spring Security JWT
- **加密**: SM-Crypto 0.3.13

### 开发工具
- **API文档**: SpringDoc OpenAPI 2.8.9
- **代码生成**: MapStruct 1.6.3
- **SQL监控**: P6Spy 3.9.1
- **工具库**: Hutool 6.0.0-M21, FastJSON 2.0.57
- **对象存储**: MinIO 8.5.17, AWS S3

### 第三方服务
- **微信**: Weixin-Java-SDK 4.7.7
- **短信**: 支持多种短信服务商
- **第三方登录**: JustAuth
- **IP地址解析**: IP2Region

## 3. 项目结构

```
goya/
├── dependencies/                    # 外部依赖版本管理
├── bom/                           # 内部依赖版本管理
├── component/                     # 通用技术组件
│   ├── component-web/             # Web层通用组件 (WebMVC, Undertow, Thymeleaf)
│   ├── component-common/          # 通用工具组件 (Hutool, OkHttp, Apache Commons)
│   ├── component-cache/           # 缓存组件
│   ├── component-db/              # 数据库组件
│   ├── component-exception/       # 异常处理组件
│   ├── component-json/            # JSON处理组件
│   ├── component-distributedid/   # 分布式ID生成组件
│   ├── component-catchlog/        # 日志捕获组件
│   ├── component-doc/             # 文档组件
│   ├── component-domain/          # 领域模型组件
│   ├── component-job/             # 定时任务组件
│   ├── component-ruleengine/      # 规则引擎组件
│   ├── component-statemachine/    # 状态机组件
│   ├── component-test-container/  # 测试容器组件
│   ├── component-bus/             # 事件总线组件
│   ├── component-captcha/         # 验证码组件
│   ├── component-crypto/          # 加密组件
│   └── component-pojo/            # POJO对象组件
├── module/                        # 业务功能模块
│   ├── module-redis/              # Redis集成模块
│   ├── module-kafka/              # Kafka集成模块
│   ├── module-elasticsearch/      # Elasticsearch集成模块
│   ├── module-websocket/          # WebSocket集成模块
│   ├── module-ip2region/          # IP地址解析模块
│   ├── module-sms/                # 短信服务模块
│   ├── module-justauth/           # 第三方登录模块
│   ├── module-wechat/             # 微信集成模块
│   ├── module-tenant/             # 多租户模块
│   ├── module-rest/               # REST服务模块
│   ├── module-identity/           # 身份认证模块
│   ├── module-jpa/                # JPA集成模块
│   ├── module-mybatis-plus/       # MyBatis-Plus集成模块
│   └── module-domain/             # 领域模块
├── security/                      # 安全认证模块
│   ├── security-core/             # 安全核心组件
│   ├── security-authorization/    # 授权组件
│   ├── security-authentication/   # 认证组件
│   ├── security-authorization-server/  # 授权服务器
│   └── security-resource-server/  # 资源服务器
├── starter/                       # Spring Boot Starter
│   ├── goya-common-starter/       # 通用Starter
│   ├── goya-common-api-starter/   # API通用Starter
│   ├── goya-cloud-starter/        # 云原生Starter
│   ├── goya-security-resource-server-starter/  # 资源服务器Starter
│   ├── goya-security-authorization-server-starter/  # 授权服务器Starter
│   └── goya-service-starter/      # 服务Starter
├── cloud/                         # 云原生组件
│   ├── cloud-alibaba/             # 阿里云组件
│   └── cloud-dubbo/               # Dubbo组件
├── openapi/                       # OpenAPI文档
├── doc/                           # 项目文档
└── logs/                          # 日志文件
```

## 4. 模块详细说明

### 4.1 Component 组件模块

#### 核心组件
- **`component-web`**: Web层通用组件，包含WebMVC、Undertow、Thymeleaf、WebClient等Web相关功能
- **`component-common`**: 通用工具组件，包含Hutool、OkHttp、Apache Commons、BouncyCastle等常用工具库
- **`component-cache`**: 缓存通用配置和抽象接口，支持多种缓存实现
- **`component-db`**: 数据库通用配置、JPA集成、数据源管理、多租户支持
- **`component-exception`**: 项目统一的业务异常类和全局异常处理器
- **`component-json`**: JSON处理组件，支持安全序列化和反序列化
- **`component-distributedid`**: 分布式ID生成器，支持雪花算法等
- **`component-catchlog`**: 日志捕获组件，支持日志收集和分析
- **`component-doc`**: 文档组件，支持文档生成和管理
- **`component-domain`**: 领域模型和领域服务
- **`component-pojo`**: POJO对象，基础数据对象

#### 功能组件
- **`component-job`**: 定时任务组件，支持分布式任务调度
- **`component-ruleengine`**: 规则引擎组件，支持动态规则配置
- **`component-statemachine`**: 状态机组件，支持复杂业务流程
- **`component-captcha`**: 验证码组件，支持多种验证码类型
- **`component-crypto`**: 加密组件，支持国密算法
- **`component-bus`**: 事件总线，支持事件驱动架构
- **`component-test-container`**: 测试容器组件，支持集成测试

### 4.2 Module 业务模块

#### 基础设施模块
- **`module-redis`**: Redis客户端封装，支持单机、集群、哨兵模式
- **`module-kafka`**: Kafka生产者和消费者配置，支持消息队列
- **`module-elasticsearch`**: Elasticsearch集成，支持全文搜索
- **`module-websocket`**: WebSocket配置和消息处理，支持实时通信

#### 业务功能模块
- **`module-ip2region`**: IP地址解析，支持地理位置查询
- **`module-sms`**: 短信服务，支持多种短信服务商
- **`module-justauth`**: 第三方登录，支持多种社交平台
- **`module-wechat`**: 微信集成，支持公众号和小程序
- **`module-tenant`**: 多租户支持，支持三种多租户模式
- **`module-rest`**: REST服务，提供统一的REST接口
- **`module-identity`**: 身份认证，支持多种认证方式
- **`module-jpa`**: JPA集成，提供JPA相关功能
- **`module-mybatis-plus`**: MyBatis-Plus集成，提供MyBatis增强功能
- **`module-domain`**: 领域模块，包含业务领域模型

### 4.3 Security 安全模块

#### 核心安全组件
- **`security-core`**: 安全核心组件，提供基础安全功能
- **`security-authorization`**: 授权组件，处理权限验证
- **`security-authentication`**: 认证组件，处理用户认证
- **`security-authorization-server`**: 授权服务器，提供OAuth2授权服务
- **`security-resource-server`**: 资源服务器，保护API资源

### 4.4 Starter 自动配置模块

#### 通用Starter
- **`goya-common-starter`**: 通用功能自动配置
- **`goya-common-api-starter`**: API相关自动配置
- **`goya-cloud-starter`**: 云原生自动配置
- **`goya-security-resource-server-starter`**: 资源服务器自动配置
- **`goya-security-authorization-server-starter`**: 授权服务器自动配置
- **`goya-service-starter`**: 服务自动配置

### 4.5 Cloud 云原生模块

- **`cloud-alibaba`**: 阿里云组件，集成Nacos、Sentinel等
- **`cloud-dubbo`**: Dubbo组件，支持RPC调用

## 5. 快速开始

### 5.1 环境要求

- **JDK**: 21+
- **Maven**: 3.8+
- **数据库**: MySQL 8.0+ / PostgreSQL 13+
- **Redis**: 6.0+
- **Nacos**: 3.0+

### 5.2 构建项目

```bash
# 克隆项目
git clone https://codeup.aliyun.com/62f27d11f9861067e4e7831d/goya/goya.git
cd goya

# 构建整个项目
mvn clean install
```

### 5.3 运行基础设施

项目提供了完整的Docker Compose配置，可以快速启动所需的基础设施：

```bash
# 进入docker目录
cd doc/docker/docker-compose

# 启动基础设施
docker-compose up -d postgres redis nacos sentinel
```

### 5.4 创建微服务

1. **创建新的微服务模块**
2. **引入相应的Starter依赖**
3. **配置application.yml**
4. **实现业务逻辑**

示例微服务配置：

```xml
<dependency>
    <groupId>com.ysmjjsy.goya</groupId>
    <artifactId>goya-service-starter</artifactId>
</dependency>
```

```yaml
spring:
  application:
    name: your-service-name
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848
      config:
        server-addr: localhost:8848
        file-extension: yml

goya:
  security:
    authorization:
      target: REMOTE
```

## 6. 核心功能特性

### 6.1 安全认证体系

- **双令牌策略**: 支持JWT和Opaque Token，内部服务使用Opaque Token提高安全性，外部服务使用JWT提升性能
- **OAuth2授权**: 完整的OAuth2授权流程，支持多种授权模式
- **权限控制**: 细粒度的权限控制，支持角色和权限码
- **多租户安全**: 多租户环境下的安全隔离

### 6.2 多租户支持

- **独立数据库模式**: 每个租户使用独立的数据库
- **共享数据库独立Schema**: 共享数据库但使用独立的Schema
- **共享数据库共享Schema**: 共享数据库和Schema，通过租户ID隔离

### 6.3 微服务生态

- **服务注册发现**: 基于Nacos的服务注册与发现
- **配置中心**: 基于Nacos的配置中心，支持配置热更新
- **熔断降级**: 基于Sentinel的熔断降级
- **链路追踪**: 基于SkyWalking的分布式链路追踪

### 6.4 高性能缓存

- **多级缓存**: 支持本地缓存和分布式缓存
- **缓存策略**: 支持多种缓存策略和过期策略
- **分布式锁**: 基于Redisson的分布式锁
- **限流控制**: 基于Redis的限流控制

## 7. 项目规范

本项目遵循以下规范，以确保代码质量和可维护性：

### 7.1 开发规范
- **通用规范**: 包含项目结构、命名约定、Git提交信息等
- **Java编程语言规范**: 涵盖代码风格、注释、异常处理、日志和面向对象设计原则
- **Spring Boot/Spring Cloud框架规范**: 包含Spring Boot基础、Spring Cloud微服务、RESTful API设计以及模块与组件的细化规范

### 7.2 代码质量
- **代码检查**: 使用ESLint、Prettier、Stylelint等工具保证代码质量
- **测试覆盖**: 支持单元测试、集成测试和端到端测试
- **文档规范**: 使用OpenAPI 3.0规范生成API文档

## 8. 计划支持
- 增加module: openim全面支持
- 增加module: springai 全面支持
- 增加module: oss、s3、minio支持
- 增加module: 大疆上云api支持.增加飞控平台

## 9. 贡献指南

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 10. 许可证

本项目采用 Apache License 2.0 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 11. 联系方式

- **项目地址**: https://github.com/GoyaDo/goya
- **官方网站**: https://www.ysmjjsy.com
- **邮箱**: chenjie@ysmjjsy.com
- **开发者**: goya

---

**Goya** - 让微服务开发更简单、更高效！
