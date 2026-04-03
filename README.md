# user-service

基于 Spring Boot 的用户服务示例项目，包含：

- `user-service-api`：对外 API 契约（接口 + DTO）
- `user-service`：服务实现（Web、MyBatis、Nacos、RocketMQ Stream）

## 项目结构

```text
user-service/
├── user-service-api/               # API 契约模块（jar）
│   └── src/main/java/com/example/user/api
│       ├── StockApi.java           # 库存/用户查询接口定义
│       └── dto/                    # 请求/响应 DTO
└── user-service/                   # 服务实现模块
    ├── src/main/java/com/example/userservice
    │   ├── controller/             # HTTP 控制器
    │   ├── dao/                    # MyBatis DAO
    │   ├── dto/                    # 领域对象
    │   └── service/                # 业务服务
    └── src/main/resources
        ├── application.yml         # 配置文件
        └── mapper/                 # MyBatis XML
```

## 技术栈

- Java 8
- Spring Boot 2.7.18
- Spring Cloud 2021.0.5
- Spring Cloud Alibaba 2021.0.5.0
- MyBatis
- MySQL
- RocketMQ（基于 Spring Cloud Stream Binder）
- Nacos（服务注册发现）

## 核心能力

### 1) 库存查询与扣减

通过 `StockApi` 暴露：

- `GET /api/stocks/{skuCode}`：查询库存
- `POST /api/stocks/deduct`：扣减库存

> 当前库存为内存 `ConcurrentHashMap` 示例实现，适合演示，不适合生产。

### 2) 用户列表查询

- `GET /api/user/list`：查询用户列表（数据库）

### 3) 测试查询接口

- `GET /api/test/queryUserInfoById?id=xxx`：按用户 ID 查询用户信息

### 4) 消息驱动积分增加

监听 `add-bonus-topic` 消息后：

1. 通过 `bizId` 写入事件日志做幂等控制
2. 更新用户积分
3. 重复消息（唯一键冲突）直接忽略

## 本地运行

### 1. 构建 API 模块

```bash
cd user-service-api
mvn clean install
```

### 2. 启动服务模块

```bash
cd ../user-service
mvn spring-boot:run
```

默认启动端口：`8100`。

## 配置说明

主要配置位于 `user-service/src/main/resources/application.yml`，包含：

- 服务名与端口
- MySQL 数据源
- Nacos 服务发现地址
- RocketMQ NameServer 与 Stream Binding
- MyBatis Mapper 路径

## 注意事项

1. 当前配置文件包含示例数据库与中间件地址，建议在实际环境改为环境变量或配置中心管理。
2. 当前仓库为两个独立 Maven 模块，需先安装 `user-service-api` 再运行 `user-service`。
3. 若需要联调消息消费，请确保 RocketMQ、Nacos、MySQL 可访问。
