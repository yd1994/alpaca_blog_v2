package com.yd1994.alpacablog.blogservice.config.cache.keygenerator;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 自定义KeyGenerator
 *
 * @author yd
 */
@Configuration
public class CustomKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append(method.getName());
        Arrays.asList(params).stream().forEach(param -> {
            if (param != null) {
                sb.append("-");
                sb.append(param.getClass().getSimpleName());
                sb.append(":");
                sb.append(param.toString());
            }
        });
        return sb.toString();
    }

}
