Query Server 5.0
================

## 使用方法 (开发)

```bash
$ git clone https://github.com/TJSoftwareReuse/2012T03.git
$ cd 2012T03/QueryServer
```

对于Windows用户，不管用什么方法，将项目clone到本地

#### 1. 下载依赖包

- [PropertyUtil.jar](https://github.com/TJSoftwareReuse/DeliverComponents/blob/master/CM/T1/latest%20version/PropertyUtil.jar) by [Team 1](https://github.com/TJSoftwareReuse/2012T01)
- [FM.jar](https://github.com/TJSoftwareReuse/2012T03/releases/download/v1.2/FM.jar) by [Team 3](https://github.com/TJSoftwareReuse/2012T03/tree/master/FM)
- [LicenseG2_v1.1.jar](https://github.com/TJSoftwareReuse/DeliverComponents/blob/master/License/T2/1.1/LicenseG2_v1.1.jar) by [Team 2](https://github.com/TJSoftwareReuse/2012T02)
- [PM.jar](https://github.com/TJSoftwareReuse/DeliverComponents/raw/master/PM/T10/1.0/PerformanceManager.jar) by [Team 10](https://github.com/TJSoftwareReuse/2012T10)
- Gson 2.3.1
- JUnit 4.11
- log4j 1.2.17
    - hamcrest core 1.3

所有的依赖项均已上传至百度盘

链接: <http://pan.baidu.com/s/1qWkfRB2>

密码: b8k4

```bash
$ cd 2012T03/QueryServer
$ mkdir lib
$ cd lib
```

在项目根目录下新建一个名为`lib`的文件夹，将下载的jar包放入其中

#### 2. 开始开发

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
    public ArrayList<String> query(int team) throws RemoteException;
    public String query(String studentName) throws RemoteException;
}
```

过程如下: 

1. 导出jar包

    将项目中的`edu.tongji.server.stub`导出jar包，命名为`TJServer.jar`

2. 新建Java项目
3. 引入jar包
4. 开始使用

    ```java
    Registry registry = LocateRegistry.getRegistry("localhost", 2015);
    TJServerInterface serverInterface = (TJServerInterface) registry.lookup("TJServer");

    ArrayList<String> team = serverInterface.query(1); // 数字可更改
    ```

    其中`localhost`可以替换为相应的服务端IP地址，`2015`为服务端指定好的端口号

    另外，值得注意的是，此时服务端务必处于运行状态

#### 返回值说明

1. `public ArrayList<String> query(int team) throws RemoteException;`

`query`函数的返回值有三种情况(均为`ArrayList<String>`类型)，如下:

_以[]表示数组_

|返回值|说明|
|:---:|:--:|
|[xxx, xxx, xxx, xxx]|xxx均为组员名字|
|["NO LICENSE"]|服务端无法提供服务|
|["NON-EXISTENT"]|所查询的组号不存在|

2. `public String query(String studentName) throws RemoteException;`

`query`函数的返回值有三种情况(均为`String`类型)，如下:

|返回值|说明|
|:---:|:--:|
|第X组|X为相应的组号|
|NO LICENSE|服务端无法提供服务|
|NON-EXISTENT|所查询的学生姓名在名单上不存在|
