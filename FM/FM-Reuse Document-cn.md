FM复用文档 - 第三组
==============================

## 目录

- 需求
- 设计
- 接口
- 输出
- 用法
- 依赖

## 需求

1. 接受告警信息
2. 将告警信息写入日志文件
3. 设置日志文件目录
4. 设置日志文件大小限制

## 设计

我们考虑到`FM`这个组建可能会被许多其他的模块调用，所以我们采用了单例的设计模式

我们同时也保证了单例的创建以及文件的写入是线程安全的

## 接口

1. `public void generateWarningMessage(String message);`
2. `public void generateWarningMessage(String message, String logDirPath);` (Deprecated)

    | 参数 | 类型 | 备注 |
    | :------:| :------: | :------: |
    | Message | String | 告警信息 |
    | Filepath | String | (可选) |

3. `public void setLogDirPath(String dirPath);`

    | 参数 | 类型 | 备注 |
    | :------:| :------: | :------: |
    | Directory Path | String |  |

    默认目录为当前文件夹下的`log`目录

    如果所指定的目录并不存在，那么目录将会被创建

4. `public void setLogFileSizeLimitation(long fileSizeLimitation);`

    | 参数 | 类型 | 备注 |
    | :------:| :------: | :------: |
    | File Size Limitation | Integer | 单位是 'MB' (>= 256且<= 4096) |

## 输出

对于每一条告警信息，都将被追加写入到日志文件

- 日志文件名格式:

    ```
    <data:yyyy-MM-dd>_<count>.log

    e.g.
    2015-04-30_1.log
    ```

    `count`从1开始计数，如果当天的日志文件超过了所设置的日志文件大小限制，一个新的日志文件将会被创建，`count`加一

    如果日志文件储存目录被更改，那么`count`清零

- 日志消息格式:

    ```
    <yyyy-MM-dd HH:mm:ss>
    MESSAGE:
    <content:Your warning message>

    e.g.
    2015-04-30 15:30:00
    MESSAGE:
    A warning message

    ```

## 用法

首先得导入[FM.jar](https://github.com/TJSoftwareReuse/2012T03/releases/download/v1.2/FM.jar)包

```java
import edu.tongji.FaultManagement;
```

对于`FM`组件，不可以使用`new`关键字来创建，需要如下操作:

```java
FaultManagement fm = FaultManagement.getInstance(); // 获取单例

fm.setLogDirPath("./warning"); // 更改日志文件目录
fm.setLogFileSizeLimitation(512); // 更改日志文件大小上限
fm.generateWarningMessage("[Your message here!]]"); // 将告警信息写入文件
```

## 依赖

[log4j](https://github.com/apache/log4j)

___需要自己下载相应的包，然后导入到项目___

___否则会报错___

在导入`log4j`后，需要通过配置文件来设置

```java
PropertyConfigurator.configure("log4j.properties");
```
