package com.xavier.nginxlog;

import com.xavier.commom.Constant;
import com.xavier.commom.DateUtil;
import com.xavier.commom.Utils;

import java.time.LocalDateTime;
import java.util.List;

public final class AccessLog {

    // request's client ip
    // nginx: $remote_addr or $http_x_forwarded_for
    // this variable's value can be '-' if cannot get client ip.
    private String ip;

    // remote user name
    // nginx: $remote_user
    // value of this variable can be '-' if cannot get client user.
    private String remoteUser;

    // datetime of request (counting time accurate to the second)
    // nginx: $time_local or $time_iso8601 or $msec
    private LocalDateTime requestDateTime;

    // contents of client request
    // nginx: $request
    // include HTTP method, request path, and HTTP protocol
    private String request;

    // HTTP status code
    // nginx: $status
    private int status;

    // bytes of nginx send to client(not include request header)
    // nginx: $body_bytes_sent
    private int bodyBytesSent;

    // bytes of nginx send to client(include request header)
    // nginx: $bytes_sent
    private int bytesSent;

    // serial number of connection
    // nginx: $connection
    private int connection;

    // number of request that use one connection
    // nginx: $connection_requests
    private int connectionRequests;

    // whether of pipeline request
    // nginx: $pipe
    // if request is pipeline,value of this variable is 'p'.Otherwise is '.'
    private String pipe;

    // path of url that jumps to this path
    // nginx: $http_referer
    private String httpReferer;

    // nginx: $http_user_agent
    private String userAgent;

    // nginx: $request_length
    private int requestLength;

    // deal with request's time.
    // begin of start read client first bytes, and end of sent to client the
    // last bytes.
    // nginx: $request_time
    private int requestTime;

    // nginx: $upstream_response_time
    private int upstreamResponseTime;

    // nginx: $upstream_cache_status
    private String upstreamCacheStatus;

    private AccessLog(String ip, String remoteUser, LocalDateTime requestDateTime,
                     String request, int status, int bodyBytesSent, int bytesSent,
                     int connection, int connectionRequests, String pipe, String httpReferer,
                     String userAgent, int requestLength, int requestTime,
                     int upstreamResponseTime, String upstreamCacheStatus) {
        this.ip = ip;
        this.remoteUser = remoteUser;
        this.requestDateTime = requestDateTime;
        this.request = request;
        this.status = status;
        this.bodyBytesSent = bodyBytesSent;
        this.bytesSent = bytesSent;
        this.connection = connection;
        this.connectionRequests = connectionRequests;
        this.pipe = pipe;
        this.httpReferer = httpReferer;
        this.userAgent = userAgent;
        this.requestLength = requestLength;
        this.requestTime = requestTime;
        this.upstreamResponseTime = upstreamResponseTime;
        this.upstreamCacheStatus = upstreamCacheStatus;
    }

    public String getIp() {
        return ip;
    }

    public String getRemoteUser() {
        return remoteUser;
    }

    public LocalDateTime getRequestDateTime() {
        return requestDateTime;
    }

    public String getRequest() {
        return request;
    }

    public int getStatus() {
        return status;
    }

    public int getBodyBytesSent() {
        return bodyBytesSent;
    }

    public int getBytesSent() {
        return bytesSent;
    }

    public int getConnection() {
        return connection;
    }

    public int getConnectionRequests() {
        return connectionRequests;
    }

    public String getPipe() {
        return pipe;
    }

    public String getHttpReferer() {
        return httpReferer;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public int getRequestLength() {
        return requestLength;
    }

    public int getRequestTime() {
        return requestTime;
    }

    public int getUpstreamResponseTime() {
        return upstreamResponseTime;
    }

    public String getUpstreamCacheStatus() {
        return upstreamCacheStatus;
    }

    public static class Builder {

        private String ip;
        private String remoteUser;
        private LocalDateTime requestDateTime;
        private String request;
        private int status;
        private int bodyBytesSent;
        private int bytesSent;
        private int connection;
        private int connectionRequests;
        private String pipe;
        private String httpReferer;
        private String userAgent;
        private int requestLength;
        private int requestTime;
        private int upstreamResponseTime;
        private String upstreamCacheStatus;

        public Builder setIp(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder setRemoteUser(String remoteUser) {
            this.remoteUser = remoteUser;
            return this;
        }

        public Builder setRequestDateTime(String requestDateTime, String pattern) {
            if (Utils.isNoValue(requestDateTime) || Utils.isNoValue(pattern)) {
                this.requestDateTime = null;
            } else {
                this.requestDateTime = DateUtil.toLocalDateTime(requestDateTime, pattern);
            }
            return this;
        }

        public Builder setRequest(String request) {
            this.request = request;
            return this;
        }

        public Builder setStatus(String status) {
            if (Utils.isNoValue(status)) {
                this.status = -1;
            } else {
                this.status = Integer.parseInt(status);
            }
            return this;
        }

        public Builder setBodyBytesSent(String bodyBytesSent) {
            if (Utils.isNoValue(bodyBytesSent)) {
                this.bodyBytesSent = -1;
            } else {
                this.bodyBytesSent = Integer.parseInt(bodyBytesSent);
            }
            return this;
        }

        public Builder setBytesSent(String bytesSent) {
            if (Utils.isNoValue(bytesSent)) {
                this.bytesSent = -1;
            } else {
                this.bytesSent = Integer.parseInt(bytesSent);
            }
            return this;
        }

        public Builder setConnection(String connection) {
            if (Utils.isNoValue(connection)) {
                this.connection = -1;
            } else {
                this.connection = Integer.parseInt(connection);
            }
            return this;
        }

        public Builder setConnectionRequests(String connectionRequests) {
            if (Utils.isNoValue(connectionRequests)) {
                this.connectionRequests = -1;
            } else {
                this.connectionRequests = Integer.parseInt(connectionRequests);
            }
            return this;
        }

        public Builder setPipe(String pipe) {
            this.pipe = pipe;
            return this;
        }

        public Builder setHttpReferer(String httpReferer) {
            this.httpReferer = httpReferer;
            return this;
        }

        public Builder setUserAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public Builder setRequestLength(String requestLength) {
            if (Utils.isNoValue(requestLength)) {
                this.requestLength = -1;
            } else {
                this.requestLength = Integer.parseInt(requestLength);
            }
            return this;
        }

        public Builder setRequestTime(String requestTime) {
            if (Utils.isNoValue(requestTime)) {
                this.requestTime = -1;
            } else {

                this.requestTime = (int) Double.parseDouble(requestTime) * 1000;
            }
            return this;
        }

        public Builder setUpstreamResponseTime(String upstreamResponseTime) {
            if (Utils.isNoValue(upstreamResponseTime)) {
                this.upstreamResponseTime = -1;
            } else {
                this.upstreamResponseTime = Integer.parseInt(upstreamResponseTime) * 1000;
            }
            return this;
        }

        public Builder setUpstreamCacheStatus(String upstreamCacheStatus) {
            this.upstreamCacheStatus = upstreamCacheStatus;
            return this;
        }

        public AccessLog build() {
            AccessLog al = new AccessLog(this.ip, this.remoteUser, this.requestDateTime,
                    this.request, this.status, this.bodyBytesSent, this.bytesSent,
                    this.connection, this.connectionRequests, this.pipe, this.httpReferer,
                    this.userAgent, this.requestLength, this.requestTime,
                    this.upstreamResponseTime, this.upstreamCacheStatus);
            return al;
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n")
                .append("\t").append("ip: ").append(ip).append(",\n")
                .append("\t").append("remoteUser: ").append(remoteUser).append(",\n")
                .append("\t").append("requestDateTime: ").append(requestDateTime).append(",\n")
                .append("\t").append("request: ").append(request).append(",\n")
                .append("\t").append("status: ").append(status).append(",\n")
                .append("\t").append("bodyBytesSent: ").append(bodyBytesSent).append(",\n")
                .append("\t").append("bytesSent: ").append(bytesSent).append(",\n")
                .append("\t").append("connection: ").append(connection).append(",\n")
                .append("\t").append("connectionRequests: ").append(connectionRequests).append(",\n")
                .append("\t").append("pipe: ").append(pipe).append(",\n")
                .append("\t").append("httpReferer: ").append(httpReferer).append(",\n")
                .append("\t").append("userAgent: ").append(userAgent).append(",\n")
                .append("\t").append("requestLength: ").append(requestLength).append(",\n")
                .append("\t").append("requestTime: ").append(requestTime).append(",\n")
                .append("\t").append("upstreamResponseTime: ").append(upstreamResponseTime).append(",\n")
                .append("\t").append("upstreamCacheStatus: ").append(upstreamCacheStatus).append(",\n")
                .append("}\n");

        return sb.toString();
    }
}
