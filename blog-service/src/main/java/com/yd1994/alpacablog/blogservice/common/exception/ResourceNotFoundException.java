package com.yd1994.alpacablog.blogservice.common.exception;

/**
 * <p>资源不存在异常</p>
 *
 * @author yd
 */
public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException() {
        super("资源不存在");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
