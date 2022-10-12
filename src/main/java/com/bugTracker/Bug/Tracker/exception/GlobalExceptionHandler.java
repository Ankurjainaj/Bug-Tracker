package com.bugTracker.Bug.Tracker.exception;

import com.bugTracker.Bug.Tracker.dto.ErrorInfo;
import com.bugTracker.Bug.Tracker.dto.ResponseModel;
import com.bugTracker.Bug.Tracker.entity.ExceptionLog;
import com.bugTracker.Bug.Tracker.service.RequestLogService;
import com.bugTracker.Bug.Tracker.utils.Utils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    RequestLogService requestLogService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, ValidationException.class, MissingServletRequestParameterException.class})
    @ResponseBody
    public ResponseEntity<?> handleValidationExceptions(Exception ex, HttpServletRequest request) {
        List<ErrorInfo> errors = new ArrayList<>();
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) ex;
            methodArgumentNotValidException.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                fieldName = getFieldName(fieldName);
                String errorMessage = error.getDefaultMessage();
                errors.add(new ErrorInfo(fieldName, errorMessage));
            });
        }
        if (ex instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException methodArgumentNotValidException = (MissingServletRequestParameterException) ex;
            String fieldName = methodArgumentNotValidException.getParameterName();
            String errorMessage = methodArgumentNotValidException.getMessage();
            errors.add(new ErrorInfo(fieldName, errorMessage));
        }
        if (ex instanceof ValidationException) {
            ValidationException validationException = (ValidationException) ex;
            String messageArray[] = validationException.getMessage().split(" ", 2);
            errors.add(new ErrorInfo(messageArray[0], messageArray[1]));
        }
        ResponseModel responseModel = ResponseModel.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(errors.get(0).getField() + " is invalid")
                .currentServerTime(Utils.getCurrentServerTime()).build();
        logRequest(request, responseModel, ex.getMessage());
        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseBody
    public ResponseEntity<?> handleIllegalArgumentException(Exception ex, HttpServletRequest request) {

        ResponseModel responseModel = ResponseModel.builder()
                .status(400)
                .currentServerTime(Utils.getCurrentServerTime())
                .message(ex.getMessage()).build();
        logRequest(request, responseModel, ex.getMessage());
        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidFormatException.class, HttpMessageNotReadableException.class, JSONException.class})
    @ResponseBody
    public ResponseEntity<?> handleHttpMessageNotReadableException(Exception exception, HttpServletRequest request) {
        String field = null;
        String path = "";
        if (exception instanceof HttpMessageNotReadableException) {
            HttpMessageNotReadableException httpMessageNotReadableException = (HttpMessageNotReadableException) exception;

            if (httpMessageNotReadableException.getCause() instanceof InvalidFormatException) {
                InvalidFormatException invalidFormatException = (InvalidFormatException) httpMessageNotReadableException.getCause();
                List<JsonMappingException.Reference> list = invalidFormatException.getPath();
                for (JsonMappingException.Reference ref : list) {
                    if (ref.getFieldName() == null) {
                        path += "[" + ref.getIndex() + "].";
                    } else {
                        path += ref.getFieldName();
                    }
                }
                field = path;
            } else if (httpMessageNotReadableException.getCause() instanceof MismatchedInputException) {
                MismatchedInputException mismatchedInputException = (MismatchedInputException) httpMessageNotReadableException.getCause();
                field = mismatchedInputException.getPath().get(0).getFieldName();
            } else if (httpMessageNotReadableException.getCause() instanceof JsonMappingException) {
                JsonMappingException jsonMappingException = (JsonMappingException) httpMessageNotReadableException.getCause();
                field = jsonMappingException.getPath().get(0).getFieldName();
            } else if (httpMessageNotReadableException.getCause() instanceof JsonParseException) {
                return new ResponseEntity<>(ResponseModel.builder().message("Please provide valid Json request.").status(HttpStatus.BAD_REQUEST.value()).currentServerTime(Utils.getCurrentServerTime()).build(), HttpStatus.BAD_REQUEST);
            }
        }

        ResponseModel responseModel = new ResponseModel();
        responseModel.setStatus(HttpStatus.BAD_REQUEST.value());
        responseModel.setMessage((field == null ? "Request" : field) + " is invalid");
        responseModel.setCurrentServerTime(Utils.getCurrentServerTime());

        logRequest(request, responseModel, exception.getMessage());
        return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);

    }

    public String getFieldName(String filedName) {
        String fieldName = "";
        StringTokenizer stringTokenizer = new StringTokenizer(filedName, ".");
        while (stringTokenizer.hasMoreTokens()) {
            fieldName = stringTokenizer.nextToken();
        }
        return fieldName;
    }

    private void logRequest(HttpServletRequest request, ResponseModel responseModel, String message) {
        ContentCachingRequestWrapper cachedRequest = new ContentCachingRequestWrapper(request);
        try {
            ExceptionLog requestLog = requestLogService.createRequestLog(cachedRequest, cachedRequest.getRequestURI(), responseModel, message);
            requestLogService.addLog(requestLog);
        } catch (Exception e) {
            //Ignoring any exception while creating log request
            log.error("Unable to convert to json string");
        }
    }
}
