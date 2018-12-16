package com.yd1994.alpacablog.blogservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * @author yd
 */
public class AccountServiceClientFallback implements AccountServiceClient {

    @Override
    public Principal getAccount(String accountName) {
        return null;
    }
}
