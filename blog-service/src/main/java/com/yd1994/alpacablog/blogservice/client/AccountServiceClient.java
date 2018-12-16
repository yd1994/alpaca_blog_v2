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
@FeignClient(name = "auth-service", fallback = AccountServiceClientFallback.class)
public interface AccountServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users/{accountName}")
    Principal getAccount(@PathVariable("accountName") String accountName);

}
