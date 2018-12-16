package com.yd1994.alpacablog.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/oauth")
public class OauthController {

    @Autowired
    private RedisTokenStore redisTokenStore;

    @DeleteMapping("/logout")
    public ResponseEntity exit(HttpServletRequest request) {
        logout(request);
        return ResponseEntity.ok().build();
    }

    private void logout(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        authorization = authorization.toLowerCase();
        if (authorization != null && authorization.contains("bearer")){
            String tokenId = authorization.substring("bearer".length()+1);
            redisTokenStore.removeAccessToken(tokenId);
        }
    }

}
