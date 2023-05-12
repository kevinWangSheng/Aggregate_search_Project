
### 主流框架 & 特性

- Spring Boot 2.7.3
- Spring MVC
- MyBatis + MyBatis Plus 
- Spring Boot 调试工具和项目处理器
- Spring AOP 切面编程
- Spring Scheduler 定时任务
- Spring 事务注解

### 数据存储

- MySQL 数据库
- Elasticsearch 搜索引擎
- logstash数据同步

### 解析工具
- Jsoup+HtmlUnit页面解析抓取
- Hutools解析Json

### 数据库配置
在application.yml中把对应的ip地址改成你mysql对应的ip地址
修改redis等等。

### 统一的数据处理接口
DataSource：制定一个统一的数据处理接口，这里使用到了对应的适配器模式，如果你需要进行对应的类型数据查询，需要按照每一个类型对应的方法参数进行定制化配置参数。
防止了其他不同数据的接入导致数据接口的混乱。

数据的类型主要使用了枚举类，这里使用Map进行对应的注册，防止每一次都要进行switch或者是if-else进行判断对应接入的数据类型。
在容器启动初始化的时候就进行对应的数据类型注册。当需要搜索数据，调用对应的类型接口的时候，只需要使用map.get(key)就能够获取到对应类型的数据接口，完成搜索返回数据。
