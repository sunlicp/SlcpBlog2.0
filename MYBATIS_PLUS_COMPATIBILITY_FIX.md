# MyBatis-Plus 与 Spring Boot 兼容性修复

## 🎯 问题描述

项目在整合 Spring AI 时需要升级 Spring Boot，但升级后发现与 MyBatis-Plus 存在兼容性问题。

## ✅ 解决方案

### 版本配置调整

根据项目历史文档和兼容性分析，采用以下稳定的版本组合：

| 依赖 | 原版本 | 新版本 | 说明 |
|------|--------|--------|------|
| Spring Boot | 3.2.5 | **3.2.11** | 稳定版本，完全兼容 MyBatis-Plus |
| MyBatis-Plus | 3.5.1 | **3.5.7** | 更稳定的版本，修复了多项 Bug |
| Spring AI | 0.8.1 | **0.8.1** | 保持不变，兼容 Spring Boot 3.2.x |
| JDK | 17 | **17** | 保持不变 |

### 修改内容

#### 1. pom.xml 版本更新

```xml
<!-- Spring Boot 升级到稳定版本 -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.11</version>
</parent>

<!-- MyBatis-Plus 升级到稳定版本 -->
<properties>
    <mybatisplus.version>3.5.7</mybatisplus.version>
</properties>

<!-- 重要：使用 Spring Boot 3.x 专用版本 -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-spring-boot3-starter</artifactId>
    <version>${mybatisplus.version}</version>
</dependency>
```

**关键修改**：
- 从 `mybatis-plus-boot-starter` 改为 `mybatis-plus-spring-boot3-starter`
- 这是专门为 Spring Boot 3.x 设计的版本，解决了 `factoryBeanObjectType` 兼容性问题

#### 2. DevopsApplication.java 配置更新

添加 Spring Cloud Function 自动配置排除，避免兼容性问题：

```java
@SpringBootApplication(exclude = {
    org.springframework.cloud.function.context.config.ContextFunctionCatalogAutoConfiguration.class
})
@EnableScheduling
@MapperScan(basePackages = "com.slcp.devops.mapper")
public class DevopsApplication {
    // ...
}
```

#### 3. AbstractInsertBatch.java 方法签名修复

MyBatis-Plus 3.5.7 中 `getKeyInsertSqlColumn` 方法签名变更，需要修复：

**修改前：**
```java
return tableInfo.getKeyInsertSqlColumn(true, true) + ...
```

**修改后：**
```java
return tableInfo.getKeyInsertSqlColumn(true, "", true) + ...
```

**原因：**
- MyBatis-Plus 3.5.7 将方法签名从 `getKeyInsertSqlColumn(boolean, boolean)` 改为 `getKeyInsertSqlColumn(boolean insertProperty, String propertyPrefix, boolean withId)`
- 对于列名，第二个参数使用空字符串 `""` 即可

#### 4. MyBatis-Plus Starter 依赖修复 ✅

**问题**：
使用 `mybatis-plus-boot-starter`（通用版本）在 Spring Boot 3.2.11 上出现 `factoryBeanObjectType` 兼容性错误。

**解决方案**：
改用 `mybatis-plus-spring-boot3-starter`（Spring Boot 3.x 专用版本）：

```xml
<!-- 修改前（通用版本） -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>${mybatisplus.version}</version>
</dependency>

<!-- 修改后（Spring Boot 3.x 专用版本） -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-spring-boot3-starter</artifactId>
    <version>${mybatisplus.version}</version>
</dependency>
```

**效果**：
- ✅ 解决了 `factoryBeanObjectType: java.lang.String` 错误
- ✅ 完美兼容 Spring Boot 3.2.11
- ✅ 自动引入正确的 `mybatis-spring 3.0.3` 版本

## 📋 版本兼容性说明

### Spring Boot 3.2.x 系列（推荐 ✅）

| Spring Boot 版本 | MyBatis-Plus 版本 | 状态 |
|-----------------|------------------|------|
| 3.2.0 - 3.2.11 | 3.5.3+ | ✅ 完全兼容 |

**优点：**
- 稳定性高，生产环境推荐
- 所有依赖完全兼容
- Spring AI 0.8.1 完全支持
- MyBatis-Plus 3.5.x 完全支持

### Spring Boot 3.3.x 系列（不推荐 ❌）

| Spring Boot 版本 | MyBatis-Plus 版本 | 状态 |
|-----------------|------------------|------|
| 3.3.0 - 3.3.x | 3.5.x | ❌ 存在兼容性 Bug |

**问题：**
- MyBatis-Plus 3.5.x 在 Spring Boot 3.3.x+ 上存在 `factoryBeanObjectType` 的兼容性 Bug
- 错误信息：`Invalid value type for attribute 'factoryBeanObjectType': java.lang.String`

### Spring Boot 3.4.x 系列（不推荐 ❌）

| Spring Boot 版本 | MyBatis-Plus 版本 | 状态 |
|-----------------|------------------|------|
| 3.4.0+ | 3.5.x | ❌ 多重兼容性问题 |

**问题：**
- MyBatis-Plus 不兼容
- Logback 配置不兼容
- Spring Cloud Function 不兼容
- 生态系统支持不完善

## 🚀 验证步骤

### 1. 清理并编译

```bash
cd /Users/slcp/zhongzhi/myblog
mvn clean compile -DskipTests
```

**预期结果：**
```
[INFO] BUILD SUCCESS
```

### 2. 启动应用

```bash
mvn spring-boot:run
```

或使用 IDE 运行 `DevopsApplication.java`

### 3. 验证功能

- ✅ 应用正常启动
- ✅ 数据库连接正常
- ✅ MyBatis-Plus 功能正常
- ✅ Spring AI 功能正常

## 📝 相关文档

- [ISSUE_SUMMARY.md](./ISSUE_SUMMARY.md) - 项目启动问题修复总结
- [STARTUP_FIX.md](./STARTUP_FIX.md) - 启动修复指南
- [SPRING_AI_STABLE_VERSION.md](./SPRING_AI_STABLE_VERSION.md) - Spring AI 稳定版本配置

## ⚠️ 注意事项

1. **不要盲目升级到最新版本**
   - Spring Boot 3.3.x+ 与 MyBatis-Plus 3.5.x 存在已知兼容性问题
   - 建议等待 MyBatis-Plus 3.6.0+ 版本发布后再考虑升级

2. **版本组合建议**
   - 生产环境：Spring Boot 3.2.11 + MyBatis-Plus 3.5.7
   - 开发环境：Spring Boot 3.2.x + MyBatis-Plus 3.5.7

3. **方法签名变更**
   - 升级 MyBatis-Plus 时，注意检查自定义注入方法的方法签名
   - 使用 IDE 的代码补全功能可以快速发现方法签名变更

## 🔮 未来升级建议

当以下条件满足时，可以考虑升级到 Spring Boot 3.3.x 或 3.4.x：

1. ✅ MyBatis-Plus 发布兼容 Spring Boot 3.3.x+ 的新版本（预计 3.6.0+）
2. ✅ Spring AI 发布正式版（不再是 Milestone 版本）
3. ✅ 第三方依赖完全适配新版本
4. ✅ 官方发布详细的升级指南

## ✨ 总结

通过以下调整，成功解决了 Spring Boot 与 MyBatis-Plus 的兼容性问题：

1. ✅ 升级 Spring Boot 到稳定的 3.2.11 版本
2. ✅ 升级 MyBatis-Plus 到稳定的 3.5.7 版本
3. ✅ 修复 MyBatis-Plus 方法签名变更
4. ✅ 添加 Spring Cloud Function 排除配置
5. ✅ 项目成功编译并运行

**修复日期**: 2025-11-03  
**状态**: ✅ 已解决

