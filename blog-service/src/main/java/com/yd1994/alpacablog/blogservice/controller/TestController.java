package com.yd1994.alpacablog.blogservice.controller;

import com.yd1994.alpacablog.blogservice.client.AccountServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class TestController {

    @Autowired
    private AccountServiceClient accountServiceClient;

    @GetMapping("/test")
    public Principal test(Principal principal) {
        return principal;
    }

    @GetMapping("/test2")
    public Principal test2(Principal principal) {
        return accountServiceClient.getAccount("current");
    }
}
