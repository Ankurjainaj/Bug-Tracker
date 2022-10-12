package com.bugTracker.Bug.Tracker.service;

import com.bugTracker.Bug.Tracker.entity.ExceptionLog;
import com.bugTracker.Bug.Tracker.repository.ExceptionLogRepository;
import com.bugTracker.Bug.Tracker.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RequestLogServiceImpl implements RequestLogService {

    @Autowired
    private ExceptionLogRepository logRepository;

    @Override
    public void addLog(ExceptionLog log) {
        logRepository.save(log);
    }

    @Override
    public void addLog(
            ContentCachingRequestWrapper requestWrapper, String requestUrl, Object responseBody, String message) {
        try {
            logRepository.save(createRequestLog(requestWrapper, requestUrl, responseBody, message));
        } catch (Exception ex) {
            //Ignore if exception while creating log request
            log.error("Unable to convert to json string");
        }
    }

    public ExceptionLog createRequestLog(
            ContentCachingRequestWrapper requestWrapper, String requestUrl, Object responseBody, String message) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String responseBodyJson = responseBody == null ? "{}" : gson.toJson(responseBody);
        String requestBody = "";
        try {
            requestBody = requestWrapper.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            requestBody = "Unable to read request body";
        }
        if (requestBody.equals("")) {
            requestBody = "{}";
        }
        String urlWithParameter =
                Utils.getRequestUrlWithParameter(requestUrl, requestWrapper.getParameterMap());
        return
                ExceptionLog.builder()
                        .requestUrl(urlWithParameter)
                        .requestJson(requestBody)
                        .responseJson(responseBodyJson)
                        .creationDate(Utils.getCurrentServerTime())
                        .updationDate(Utils.getCurrentServerTime())
                        .message(message)
                        .build();
    }
}
