package com.xavier;

import com.xavier.nginxlog.AccessLog;
import com.xavier.nginxlog.LogFormatter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

/**
 * @author xavier
 * @date 2017-08-23
 */
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
