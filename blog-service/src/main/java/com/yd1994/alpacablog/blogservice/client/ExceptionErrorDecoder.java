package com.yd1994.alpacablog.blogservice.client;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionErrorDecoder implements ErrorDecoder {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public Exception decode(String s, Response response) {
        log.info(response.toString());
        return null;
    }

}
