package com.yd1994.alpacablog.authservice.service.security;

import com.yd1994.alpacablog.authservice.entity.UserDO;
import com.yd1994.alpacablog.authservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 加载用户信息
 *
 * @author yd
 */
@Service
public class MysqlUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 通过 username 查找 用户
        UserDO userDO = this.userRepository.findFirstByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        String roles = userDO.getRole();
        if (!StringUtils.isEmpty(roles)) {
            Arrays.asList(roles.split(",")).forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role)));
        }
        return new User(userDO.getUsername(), userDO.getPassword(), grantedAuthorities);
    }

}
