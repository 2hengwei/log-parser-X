package com.xavier.commom;

public class Constant {

    public static final String[] FORMAT_ARRAY = {
            "$remote_addr",
            "$http_x_forwarded_for",
            "$remote_user",
            "$time_local",
            "$time_iso8601",
            "$request",
            "$status",
            "$body_bytes_sent",
            "$bytes_sent $connection",
            "$connection_requests",
            "$msec",
            "$pipe",
            "$http_user_agent",
            "$request_length",
            "$upstream_response_time",
            "$upstream_cache_status"
    };

    public static final String IP = "ip";
    public static final String REMOTE_USER = "remoteUser";
    public static final String REQUEST_DATE_TIME = "requestDateTime";
    public static final String REQUEST = "request";
    public static final String STATUS = "status";
    public static final String BODY_BYTES_SENT = "bodyBytesSent";
    public static final String BYTES_SENT = "bytesSent";
    public static final String CONNECTION = "connection";
    public static final String CONNECTION_REQUESTS = "connectionRequests";
    public static final String PIPE = "pipe";
    public static final String HTTP_REFERER = "httpReferer";
    public static final String USER_AGENT = "userAgent";
    public static final String REQUEST_LENGTH = "requestLength";
    public static final String REQUEST_TIME = "requestTime";
    public static final String UPSTREAM_RESPONSE_TIME = "upstreamResponseTime";
    public static final String UPSTREAM_CACHE_STATUS = "upstreamCacheStatus";

    public static class Symbol {
        public final static String DOLLAR = "$";
        public final static char CHAR_DOLLAR = '$';
        public final static String HYPHEN = "-";

    }

    public static class Prop {
        public static final String LOG_FORMAT = "log.format";
    }

}
