# spring-boot-rbac

使用分支的方式，展示在 vscode 中完成一个 SpringBoot 的 RBAC 鉴权服务的具体过程，可以用来当做一些后台系统的基础模板

关键词：vscode springboot java11 jpa gradle rbac

## [1-开发环境准备](https://github.com/hezhongfeng/spring-boot-rbac/tree/session-1)

1. jdk 说明以及安装
2. gradle 说明和安装配置
3. vscode 插件推荐

## 2-初始化 SpringBoot

1. 初始化 spring-boot 项目，启动
2. 在 vscode 打开调试模式，查看调试信息

## 3-Web

1. 添加 web 依赖，启动
2. 添加根路由接口，启动，浏览器访问

## 4-详解 RBAC

1. 分析 RBAC 的数据结构
2. 分析详细的使用方法

## 5-JPA 和 Mysql

1. 创建本地数据库
2. 添加 JPA 和 MySQL 依赖，并且成功启动
3. 设计具体的 Entity
4. 启动，查看数据库

## 6-API 设计

1. 设计 Restful API 接口
2. 接口规范相关

## 7-API 实现

1. 实现 Controller 层
2. 添加 API 说明文档

## 8-Repo 和 Service

1. JpaRepository
2. Service
3. 角色和用户的 Controller

## 9-Security

1. 添加 Security，访问接口
2. 登录，访问接口
3. 完成登录权限认证
4. 登录接口
5. 添加 jjwt 依赖

## 10-Security 验证和处理

1. 添加最新的 Security 配置
2. 登录过程
3. 认证过程
4. 接口权限认证过程
5. 添加测试用户数据
6. 登录、测试接口权限

## 11-多对多关系

1. lazy 查询
2. 解决循环引用问题
3. 搭配前端联调

## 12-部署

1. 在 heroku 创建应用和数据库
2. 部署应用
3. 在 vercel 部署前端
4. 在线访问
