package com.yd1994.alpacablog.authservice.config;

import com.yd1994.alpacablog.authservice.service.security.MysqlUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * oauth 授权配置
 *
 * @author yd
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private MysqlUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private Environment environment;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("alpaca_blog_vue")
                .secret(environment.getProperty("client_vue_password"))
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("client")
                .resourceIds("auth-service", "blog-service", "statistics-service")
                .and()
                .withClient("blog_service")
                .secret(environment.getProperty("blog_service_password"))
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .scopes("server")
                .resourceIds("auth-service", "blog-service", "statistics-service")
                .and()
                .withClient("statistics_service")
                .secret(environment.getProperty("statistics_service_password"))
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .scopes("server")
                .resourceIds("auth-service", "blog-service", "statistics-service");;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(redisTokenStore())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                // 不加密
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .allowFormAuthenticationForClients();
    }

    @Bean
    public RedisTokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }


}
