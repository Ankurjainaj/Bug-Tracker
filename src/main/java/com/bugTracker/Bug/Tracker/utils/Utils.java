package com.bugTracker.Bug.Tracker.utils;

import com.bugTracker.Bug.Tracker.dto.RolesOfUser;
import com.bugTracker.Bug.Tracker.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public final class Utils {

    public static String getCurrentServerTime() {
        LocalDateTime ldt = Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.of("Asia/Kolkata")).toLocalDateTime();
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return ldt.format(format1);
    }

    public static String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    public static String getRequestUrlWithParameter(String url, Map<String, String[]> parameterMap) {
        StringBuilder requestUrl = new StringBuilder(url);
        if (parameterMap.size() == 0) {
            return requestUrl.toString();
        }
        requestUrl.append("?");
        for (Iterator<Map.Entry<String, String[]>> entryIterator = parameterMap.entrySet().iterator();
             entryIterator.hasNext(); ) {
            Map.Entry<String, String[]> valueEntry = entryIterator.next();
            requestUrl.append(valueEntry.getKey());
            requestUrl.append("=");
            if (valueEntry.getValue().length == 1) {
                requestUrl.append(valueEntry.getValue()[0]);
            } else {
                for (Iterator<String> valueIterator = Arrays.stream(valueEntry.getValue()).iterator();
                     valueIterator.hasNext(); ) {
                    requestUrl.append(valueIterator.next());
                    if (valueIterator.hasNext()) {
                        requestUrl.append(",");
                    }
                }
            }
            if (entryIterator.hasNext()) {
                requestUrl.append("&");
            }
        }
        return requestUrl.toString();
    }

    public static boolean isAdmin(User loggedUser) {
        boolean admin = false;
        if (loggedUser.getRoles() != null && loggedUser.getRoles().isEmpty())
            return false;
        for (RolesOfUser role : loggedUser.getRoles()) {
            if (role.getStatus() != 5 && role.getRole().equalsIgnoreCase("admin")) {
                admin = true;
                break;
            }
        }
        return admin;
    }
}
