package edu.project3.model;

import java.time.ZonedDateTime;

public record LogRecord(
    String ipaddress,
    ZonedDateTime timestamp,
    Method method,
    String url,
    String protocol,
    HttpStatus statusCode,
    long bytesSent,
    String referer,
    String useragent
) {}
