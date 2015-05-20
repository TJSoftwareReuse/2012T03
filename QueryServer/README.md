Query Server
============

## 使用方法 (开发)

```bash
$ git clone https://github.com/TJSoftwareReuse/2012T03.git
$ cd 2012T03/QueryServer
```

对于Windows用户，不管用什么方法，将项目clone到本地

#### 1. 下载依赖包

- [CM.jar](https://github.com/TJSoftwareReuse/DeliverComponents/raw/master/CM/T3/1.0/CM.jar) by [Team 3](https://github.com/TJSoftwareReuse/2012T03/tree/master/CM)
- [FM.jar](https://github.com/TJSoftwareReuse/2012T03/releases/download/v1.2/FM.jar) by [Team 3](https://github.com/TJSoftwareReuse/2012T03/tree/master/FM)
- [License.jar](https://github.com/TJSoftwareReuse/DeliverComponents/raw/master/License/T10/1.0/License.jar) by [Team 10](https://github.com/TJSoftwareReuse/2012T10)
- [PM.jar](https://github.com/TJSoftwareReuse/DeliverComponents/raw/master/PM/T10/1.0/PerformanceManager.jar) by [Team 10](https://github.com/TJSoftwareReuse/2012T10)

```bash
$ cd 2012T03/QueryServer
$ mkdir lib
$ cd lib
```

在项目根目录下新建一个名为`lib`的文件夹，将上述四个组件的jar包放入其中

#### 2. 安装Gradle

(本项目用Gradle来管理依赖，依赖信息都写在`build.gradle`中，使用方法自行Google或百度= =......)

```bash
$ gradle eclipse # It will generates all Eclipse files.
```

(对于Windows用户，可能需要自行摸索)

#### 2*. 不使用Gradle

对于死也不想尝试`Gradle`的同学......请看这里

除了上述四个组件，还有以下两个依赖:

- log4j(Version 1.2.17)
- jUnit(Version 4.11)

(`log4j`版本有点老)

将这两个库的jar包也放到`lib`文件中

#### 3. 开始开发

1. 打开 `Eclipse`
2. 菜单栏 `File` -> `Import`
3. 选择 `General` -> `Existing Projects into Workspace`
4. Select root directory
5. 菜单栏 `Project` -> `Properties`
6. 左侧，选择 `Java Build Path`
7. 右侧，选择 `Libraries` -> `Add JARs`，将lib文件夹中的jar包全部导入

## 使用方法 (用户)

因为本项目基于RMI(Remote Method Invocation, 远程方法调用)开发，所以需要在客户端引入远程对象的接口

其中提供如下接口:

```java
public interface TJServerInterface extends Remote {
    public String query(String studentName) throws RemoteException;
}
```

过程如下: 

1. 下载jar包

    [TJServer.jar](https://github.com/TJSoftwareReuse/2012T03/releases/download/v1.3/TJServer.jar)

2. 新建Java项目
3. 引入jar包
4. 开始使用

    ```java
    Registry registry = LocateRegistry.getRegistry("localhost", 2015);
    TJServerInterface serverInterface = (TJServerInterface) registry.lookup("TJServer");

    String team = serverInterface.query("xxx");
    ```

    其中`localhost`可以替换为相应的服务端IP地址, `2015`为服务端指定好的端口号

    另外，值得注意的是，此时服务端务必处于运行状态

#### 返回值说明

`query`函数的返回值有三种情况(均为`String`类型)，如下:

|返回值|说明|
|:---:|:--:|
|第X组|X为相应的组号|
|NO LICENSE|服务端无法提供服务|
|NON-EXISTENT|所查询的学生姓名在名单上不存在|
