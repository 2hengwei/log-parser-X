
# log-parser-X

![License](https://img.shields.io/badge/License-MIT-brightgreen.svg)
![done](http://progressed.io/bar/85?title=done)

Author: zhengwei

A libiary of log parser for nginx.

## Overview

Recently, I have a job that cutting nginx access log to use later. But there were many problems I will be faced, the problem is there was many log formats in different projects. It is very cumbersome if I handle as per log format. 

`log-parser-X` have only one functional that convert log string to `AccessLog` Object. In order to adapt to diversity of the format, this libiary use nginx format expression that `log_format` directive where in `nginx.conf` config file. You just paste it into the configuration file (such as xml, yaml or properties etc.) as is.

## Prepare

There are only two public API and Two Class that you must used.

API:
```Java
LogFormatter.init(String logFormat);
LogFormatter.format(String logText);
```

Class:
* LogFormatter
* AccessLog

### Explain

This tool blocks the internal details, it's so simple that you only need use two classes and two APIs to complete the conversion of the log text to the entry class.

#### LogFormatter.init(String)

This API accept a String of logFormat that `log_format` directive's value where in `nginx.conf` config file.
It's return `void` and can throw runtime exception if you give an illegal string.

It is worth noting that the class `LogFormatter` is singletion, that means, if you take specific "format string" for parse your log file, `LogFormatter` only can parse a log file that conforms to "this formatting string" in the application's life cycle.

#### LogFormatter.format(String)

This API accept a line of log text as parameter, which should match the formatting string that you specificed in the `LogFormatter.init` method.

It return `AccessLog` instance when process sucess or `null` if an error produced during the processing.

#### AccessLog

The class `AccessLog` is a oridinary JavaBean, but it only contains getter methods but no setter methods.

## Usage

```java
public class Example {

    private static final String FORMAT = "$remote_addr - $remote_user [$time_local] \"$request\" $status" +
            " $body_bytes_sent \"$http_referer\" \"$http_user_agent\"  $request_time" +
            "\"$upstream_response_time\" \"$upstream_cache_status\"";

    public static void main(String[] args) throws IOException {
        File file = new File("/data/log_data/tile_oneline.log");
        LogFormatter.init(FORMAT);

        Files.lines(file.toPath(), Charset.forName("UTF-8"))
                .forEach(line -> {
                    AccessLog accessLog = LogFormatter.format(line);
                    System.out.println(accessLog.toString());
                });

    }
}

```











