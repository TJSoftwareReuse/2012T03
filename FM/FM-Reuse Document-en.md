Reuse Ducument for FM - Team 3
==============================

## Contents

- Requirement
- Design
- API
- Output
- Usage
- Dependency

## Requirement

1. Receiving the warning message
2. Writing warning messages to the log file
3. Setting log directory path
4. Setting log file size limitation

## Design

We think that the FM conponent may be used by many other components, so we applied singleton pattern on this class.

We also ensured the creationg of the singleton and the file writing operation thread safe, which makes it easy to use.

When you want to use it, you just need to pass one parameter - the message content. You can change log directory path and log file size limitation.

## API

1. `public void generateWarningMessage(String message);`
2. `public void generateWarningMessage(String message, String logDirPath);` (Deprecated)

    | Parameter | Type | Note |
    | :------:| :------: | :------: |
    | Message | String | Warning message |
    | Filepath | String | (Optional) |

3. `public void setLogDirPath(String dirPath);`

    | Parameter | Type | Note |
    | :------:| :------: | :------: |
    | Directory Path | String |  |

    Default directory path is `./log`.

    If the directory you assigned doesn't exist, it will be created.

4. `public void setLogFileSizeLimitation(long fileSizeLimitation);`

    | Parameter | Type | Note |
    | :------:| :------: | :------: |
    | File Size Limitation | Integer | The unit is 'MB' (>= 256 and <= 4096) |

## Output

It will generate a text file and each message will be appended to it.

- Log filename format:

    ```
    <data:yyyy-MM-dd>_<count>.log

    e.g.
    2015-04-30_1.log
    ```

    `count` begins at 1, if current log file exceeds the size limitation, `count` will increase by 1.

    When the log directory is changed, `count` will start over at 1.

- Log message format:

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

Firstly, you should import [FM.jar](https://github.com/TJSoftwareReuse/2012T03/releases/download/v1.2/FM.jar)

```java
import edu.tongji.FaultManagement;
```

Then, you don't need to use 'new' keyword for initializing. Just do like this:

```java
FaultManagement fm = FaultManagement.getInstance(); // Get the singleton instance

fm.setLogDirPath("./warning"); // Change the log directory
fm.setLogFileSizeLimitation(512); // Change the size limitation
fm.generateWarningMessage("[Your message here!]]"); // Append message to the log file
```

## Dependency

Require JDK 1.7

[log4j](https://github.com/apache/log4j)

___You have to download it by yourself, and then import it to your project.___

___Otherwise you will get errors.___

After importing `log4j`, configuration file should be set.

```java
PropertyConfigurator.configure("log4j.properties");
```
