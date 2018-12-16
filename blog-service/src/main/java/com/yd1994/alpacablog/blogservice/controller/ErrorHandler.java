package com.yd1994.alpacablog.blogservice.controller;

import com.yd1994.alpacablog.blogservice.common.exception.ResourceIsDeletedException;
import com.yd1994.alpacablog.blogservice.common.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常处理
 *
 * @author yd
 */
@ControllerAdvice
public class ErrorHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity illegalArgumentException(IllegalArgumentException e) {
        log.info("Returning HTTP 400 Bad Request: " + e.getMessage());
        return ResponseEntity.badRequest().body(getMap(e.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity resourceNotFoundException(ResourceNotFoundException e) {
        log.debug("Returning HTTP 404 notFound: " + e.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ResourceIsDeletedException.class)
    public ResponseEntity resourceIsDeletedException(ResourceIsDeletedException e) {
        log.debug("Returning HTTP 410 GONE: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.GONE).body(getMap(e.getMessage()));
    }

    private Map<String, Object> getMap(String message) {

        Map<String, Object> map = new HashMap<>();
        map.put("message", message);

        return map;
    }

}
