package com.yd1994.alpacablog.blogservice.common.exception;

/**
 * <p>该资源已经被删除异常</p>
 *
 * @author yd
 */
public class ResourceIsDeletedException extends Exception {

    public ResourceIsDeletedException() {
        super("该资源已经被删除");
    }

    public ResourceIsDeletedException(String message) {
        super(message);
    }
}
