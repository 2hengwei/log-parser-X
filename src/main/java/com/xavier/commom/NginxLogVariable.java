package com.xavier.commom;

/**
 * @author zhengwei
 * @date 2017-08-23
 */
public enum NginxLogVariable {

    remoteAddr("$remote_addr", "(\\d{0,3}\\.\\d{0,3}\\.\\d{0,3}\\.\\d{0,3})", Constant.IP),
    forwardedFor("$http_x_forwarded_for", "(\\d{0,3}\\.\\d{0,3}\\.\\d{0,3}\\.\\d{0,3})", Constant.IP),
    remoteUser("$remote_user", "([\\w\\pP]*)", Constant.REMOTE_USER),
    requestDateTimeLocal("$time_local", "([\\w:/]{20}\\s\\+\\d{4})", Constant.REQUEST_DATE_TIME),
    requestDateTimeISO8601("$time_iso8601", "([\\d-]{10}T[\\d:]{8}\\+\\d{2}:\\d{2})", Constant.REQUEST_DATE_TIME),
    request("$request", "([A-Z]{3,}\\s[\\p{Graph}\\x20]{0,}\\sHTTP/[1|2]\\.[0|1])", Constant.REQUEST),
    status("$status", "(\\d{3})", Constant.STATUS),
    bodyBytesSent("$body_bytes_sent", "(\\d+)", Constant.BODY_BYTES_SENT),
    bytesSent("$bytes_sent", "(\\d+)", Constant.BYTES_SENT),
    connection("$connection", "(\\d+)", Constant.CONNECTION),
    connectionRequests("$connection_requests", "(\\d+)", Constant.CONNECTION_REQUESTS),
    pipe("$pipe", "([p|P|\\.])", Constant.PIPE),
    httpReferer("$http_referer", "([\\S\\s]{0,}?)", Constant.HTTP_REFERER),
    userAgent("$http_user_agent", "([\\S\\s]{0,}?)", Constant.USER_AGENT),
    requestLength("$request_length", "(\\d+)", Constant.REQUEST_LENGTH),
    requestTime("$request_time", "([\\d\\.]+)", Constant.REQUEST_TIME),
    upstreamResponseTime("$upstream_response_time", "([-\\d\\.]+)", Constant.UPSTREAM_RESPONSE_TIME),
    upstreamCacheStatus("$upstream_cache_status", "([-\\w]+)", Constant.UPSTREAM_CACHE_STATUS);

    private String variableName;
    private String extractRegx;
    private String fieldName;

    NginxLogVariable(String variableName, String extractRegx, String fieldName) {
        this.variableName = variableName;
        this.extractRegx = extractRegx;
        this.fieldName = fieldName;
    }

    public static NginxLogVariable match(String nginxVariable) {
        NginxLogVariable[] nlvArr = values();
        for (NginxLogVariable nlv : nlvArr) {
            if (nlv.variableName.equals(nginxVariable)) {
                return nlv;
            }
        }
        return null;
    }

    public String getVariableName() {
        return variableName;
    }

    public String getExtractRegx() {
        return extractRegx;
    }

    public String getFieldName() {
        return fieldName;
    }
}
