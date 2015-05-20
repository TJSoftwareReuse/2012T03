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


