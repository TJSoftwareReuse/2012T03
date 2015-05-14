Reuse Ducument for FM - Team 3
==============================

## Contents

- Requirement
- Design
- API
- Output
- Usage

## Requirement

1. Receiving the warning message
2. Generating files for each warning messages

## Design

We think that the FM conponent may be used by many other components, so we applied singleton pattern on this class.

We also ensured it thread safe, which makes it easy to use.

When you want to use it, you just need to pass one or two parameters - the message content and the path of the directory for saving log files.

## API

1. `public void generateWarningMessage(String message);`

    | Parameter | Type | Note |
    | :------:| :------: | :------: |
    | Message | String | Warning message |
    | Filepath | String | (Optional) |

2. `public void setLogDirPath(String dirPath);`

    | Parameter | Type | Note |
    | :------:| :------: | :------: |
    | Directory Path | String |  |

    Default directory path is `./log`.

3. `public void setLogFileSizeLimitation(long fileSizeLimitation);`

    | Parameter | Type | Note |
    | :------:| :------: | :------: |
    | File Size Limitation | Integer | The unit is 'MB' (>= 256 and <= 4096) |

## Output

It will generate a text file and each message will be appended to it.

Log filename format:

```
<data:yyyy-MM-dd>_<count>.log

e.g.
2015-04-30_1.log
```

Log message format:

```
<yyyy-MM-dd HH:mm:ss>
MESSAGE:
<content:Your warning message>

e.g.
2015-04-30 15:30:00
MESSAGE:
A test warning message

```

## Usage

Firstly, you should import [FM.jar](https://github.com/TJSoftwareReuse/2012T03/releases/download/v1.1/FM.jar)

```java
import edu.tongji.FaultManagement;
```

Then, you don't need to use 'new' keyword for initializing. Just do like this:

```java
FaultManagement fm = FaultManagement.getInstance();

fm.setLogDirPath("./warning");
fm.setLogFileSizeLimitation(512);
fm.generateWarningMessage("[Your message here!]]");
```

## Dependency

[log4j](https://github.com/apache/log4j)

___You have to download it by yourself, and then import it to your project.___

___Otherwise you will get errors.___

After importing `log4j`, configuration file should be set.

```java
PropertyConfigurator.configure("log4j.properties");
```
