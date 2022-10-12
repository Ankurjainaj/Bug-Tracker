package com.bugTracker.Bug.Tracker.service;

import com.bugTracker.Bug.Tracker.entity.ExceptionLog;
import org.springframework.web.util.ContentCachingRequestWrapper;

public interface RequestLogService {

    void addLog(ExceptionLog log);

    void addLog(
            ContentCachingRequestWrapper requestWrapper, String requestUrl, Object responseBody, String message);

    public ExceptionLog createRequestLog(
            ContentCachingRequestWrapper requestWrapper, String requestUrl, Object responseBody, String message);
}
