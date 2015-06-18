Query Server 3.5
================

## 使用方法 (开发)

```bash
$ git clone https://github.com/TJSoftwareReuse/2012T03.git
$ cd 2012T03/QueryServer
```

对于Windows用户，不管用什么方法，将项目clone到本地

#### 1. 下载依赖包

- [CM.jar](https://github.com/TJSoftwareReuse/DeliverComponents/raw/master/CM/T3/1.0/CM.jar) by [Team 3](https://github.com/TJSoftwareReuse/2012T03/tree/master/CM)
- [FM.jar](https://github.com/TJSoftwareReuse/2012T03/releases/download/v1.2/FM.jar) by [Team 3](https://github.com/TJSoftwareReuse/2012T03/tree/master/FM)
- [License.jar](https://github.com/TJSoftwareReuse/DeliverComponents/raw/master/License/T10/1.0/License.jar) by [Team 2](https://github.com/TJSoftwareReuse/2012T02)
- [PM.jar](https://github.com/TJSoftwareReuse/DeliverComponents/raw/master/PM/T10/1.0/PerformanceManager.jar) by [Team 10](https://github.com/TJSoftwareReuse/2012T10)
- Gson 2.3.1
- JUnit 4.11
- log4j 1.2.17
    - hamcrest core 1.3

所有的依赖项均已上传至百度盘

链接: <http://pan.baidu.com/s/1qWHpiG0>

密码: gbib

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
}
```

过程如下: 

1. 下载jar包

    [TJServer2.jar](https://github.com/TJSoftwareReuse/2012T03/releases/download/v1.4/TJServer2.jar)

2. 新建Java项目
3. 引入jar包
4. 开始使用

    ```java
    Registry registry = LocateRegistry.getRegistry("localhost", 2015);
    TJServerInterface serverInterface = (TJServerInterface) registry.lookup("TJServer");

    ArrayList<String> team = serverInterface.query(1); // 数字可更改
    ```

    其中`localhost`可以替换为相应的服务端IP地址, `2015`为服务端指定好的端口号

    另外，值得注意的是，此时服务端务必处于运行状态

#### 返回值说明

`query`函数的返回值有三种情况(均为`ArrayList<String>`类型)，如下:

_以[]表示数组_

|返回值|说明|
|:---:|:--:|
|[xxx, xxx, xxx, xxx]|xxx均为组员名字|
|["NO LICENSE"]|服务端无法提供服务|
|["NON-EXISTENT"]|所查询的组号不存在|
