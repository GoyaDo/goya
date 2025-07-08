# goya

## 1. 项目简介

`goya` 是一个基于 Spring Boot 和 Spring Cloud 构建的企业级微服务框架。它旨在提供一套统一的、可复用的解决方案，帮助快速开发和部署微服务应用，并支持服务的注册与发现、负载均衡、API 网关、配置中心、熔断降级、链路追踪以及消息队列集成等功能。

## 2. 技术栈

- **核心框架**: Spring Boot, Spring Cloud
- **编程语言**: Java
- **构建工具**: Maven
- **数据存储**: (此处待补充具体使用的数据库，如 MySQL, PostgreSQL, Redis 等)
- **消息队列**: (此处待补充具体使用的消息队列，如 Kafka, RabbitMQ 等)
- **服务注册与发现**: (此处待补充具体使用的注册中心，如 Eureka, Nacos, Consul 等)
- **链路追踪**: (此处待补充具体使用的追踪工具，如 Zipkin, Jaeger 等)

## 3. 项目结构

- `pom.xml`: Maven 项目的根配置文件。
- `bom/`: Maven Bill Of Materials (BOM) 模块，用于统一管理项目依赖版本。
- `component/`: 存放项目通用的、可复用的技术组件，如 Web、REST、DB、缓存、异常处理等。
- `module/`: 存放与特定技术栈集成相关的模块，如 Redis、WebSocket、Kafka 等。
- `starter/`: 存放自定义的 Spring Boot Starter 模块，简化依赖引入和自动配置。
- `cloud/`: 存放与 Spring Cloud 相关的基础配置和客户端模块。
- `openapi/`: 存放 OpenAPI (Swagger) 相关的接口定义和文档。
- `security/`: 存放认证和授权等安全相关的模块。
- `test-service/`: 存放用于测试或示例服务的模块。
- `logs/`: 项目运行时日志的存放目录。

## 4. 模块与组件说明

### `component` 模块

- `component-web`: Web 层通用配置、拦截器、过滤器、控制器基类等。
- `component-rest`: RESTful API 通用工具类、异常处理器、响应封装。
- `component-db`: 数据库通用配置、ORM 框架集成、数据源管理。
- `component-cache`: 缓存通用配置和抽象接口，支持多种缓存实现。
- `component-exception`: 项目统一的业务异常类和全局异常处理器。
- `component-common`: 通用工具类、枚举、常量等。
- `component-dto`: 数据传输对象 (DTO)，用于接口参数和返回值。
- ... (其他 `component` 模块根据实际情况补充)

### `module` 模块

- `module-redis`: 封装 Redis 客户端操作，提供统一的 Redis 工具类。
- `module-websocket`: 封装 WebSocket 相关配置和消息处理。
- `module-kafka`: 封装 Kafka 生产者和消费者配置。
- ... (其他 `module` 模块根据实际情况补充，例如 `module-user`, `module-order` 等业务模块)

## 5. 构建与运行

### 构建项目

使用 Maven 构建整个项目：

```bash
mvn clean install
```

### 运行应用

导航到具体微服务模块（例如 `test-service` 或其他业务模块）的根目录，然后运行 Spring Boot 应用：

```bash
cd test-service
mvn spring-boot:run
# 或者通过 jar 包运行
# java -jar target/your-service-name.jar
```

## 6. 项目规范

本项目遵循以下规范，以确保代码质量和可维护性：

- **通用规范**: 包含项目结构、命名约定、Git 提交信息等。详见 [general.mdc](mdc:.cursor/rules/general.mdc)。
- **Java 编程语言规范**: 涵盖代码风格、注释、异常处理、日志和面向对象设计原则。详见 [java_language.mdc](mdc:.cursor/rules/java_language.mdc)。
- **Spring Boot/Spring Cloud 框架规范**: 包含 Spring Boot 基础、Spring Cloud 微服务、RESTful API 设计以及模块与组件的细化规范。详见 [spring_framework.mdc](mdc:.cursor/rules/spring_framework.mdc)。