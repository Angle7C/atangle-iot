# TDengine MyBatis 集成说明

本项目当前采用双数据访问栈：

- 主业务库继续使用 PostgreSQL + Jimmer
- TDengine 通过 `iot-biz` 模块里的独立 MyBatis 数据源接入

这样不会影响现有 `spring.datasource` 和 Jimmer 配置。

## 已集成内容

### 1. Maven 依赖

位于 [iot-biz/pom.xml](D:/Java/atangle-iot/iot-biz/pom.xml)：

- `org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.4`
- `com.taosdata.jdbc:taos-jdbcdriver:3.8.3`

### 2. TDengine 独立配置

位于 [application.yaml](D:/Java/atangle-iot/iot-biz/src/main/resources/application.yaml)：

```yaml
tdengine:
  datasource:
    enabled: false
    driver-class-name: com.taosdata.jdbc.rs.RestfulDriver
    url: jdbc:TAOS-RS://127.0.0.1:6041/test
    username: root
    password: taosdata
```

默认关闭。只有将 `tdengine.datasource.enabled` 改为 `true` 才会启用。

### 3. MyBatis 配置类

位于 [TDengineMybatisConfig.java](D:/Java/atangle-iot/iot-biz/src/main/java/atangle/iot/biz/config/tdengine/TDengineMybatisConfig.java)：

- 创建 TDengine 专用 `DataSource`
- 创建独立 `SqlSessionFactory`
- 创建独立 `SqlSessionTemplate`
- 扫描 `atangle.iot.biz.tdengine.mapper` 下的 Mapper

### 4. 示例 Mapper 与 Service

- Mapper: [TDengineQueryMapper.java](D:/Java/atangle-iot/iot-biz/src/main/java/atangle/iot/biz/tdengine/mapper/TDengineQueryMapper.java)
- SQL Provider: [TDengineSqlProvider.java](D:/Java/atangle-iot/iot-biz/src/main/java/atangle/iot/biz/tdengine/mapper/TDengineSqlProvider.java)
- Service: [TDengineQueryService.java](D:/Java/atangle-iot/iot-biz/src/main/java/atangle/iot/biz/tdengine/service/TDengineQueryService.java)

当前示例能力：

- 按表查询最新数据
- 支持动态列名
- 默认限制返回条数，避免无界查询

## 使用方式

启用配置后，可直接注入：

```java
@Resource
private TDengineQueryService tdengineQueryService;
```

调用示例：

```java
List<Map<String, Object>> rows =
        tdengineQueryService.selectLatest("test.meters", List.of("ts", "temperature"), 20);
```

## 说明

- 这里没有复用 Jimmer，因为 TDengine 更适合通过独立查询模型接入。
- 当前先提供最小可用的 MyBatis 查询骨架，适合作为设备时序、属性上报、事件流水的查询入口。
- 如果后续你要接“设备物模型时序数据”，建议下一步继续补：
  - 统一时序表命名规则
  - 设备维度查询 Mapper
  - 时间范围查询
  - 插入上报数据的 Writer Mapper
