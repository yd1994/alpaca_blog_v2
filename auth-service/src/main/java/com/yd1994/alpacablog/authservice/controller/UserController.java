package com.yd1994.alpacablog.authservice.controller;

import com.yd1994.alpacablog.authservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTokenStore redisTokenStore;


    @GetMapping("/current")
    public ResponseEntity getUser(Principal principal) {
        log.debug("getUser:" + principal.getName());
        return ResponseEntity.ok(principal);
    }

    @PreAuthorize("#oauth2.hasAnyScope('server', 'client') and hasAuthority('admin')")
    @PutMapping("/current")
    public ResponseEntity changePassword(Principal principal, String oldPassword, String newPassword, HttpServletRequest request) {
        Assert.hasText(oldPassword, "输入密码有误");
        Assert.hasText(newPassword, "输入密码有误");
        this.userService.updateUserPassword(oldPassword, newPassword, principal.getName());
        String authorization = request.getHeader("Authorization");
        authorization = authorization.toLowerCase();
        if (authorization != null && authorization.contains("bearer")){
            String tokenId = authorization.substring("bearer".length()+1);
            redisTokenStore.removeAccessToken(tokenId);
        }
        return ResponseEntity.ok().build();
    }

}
