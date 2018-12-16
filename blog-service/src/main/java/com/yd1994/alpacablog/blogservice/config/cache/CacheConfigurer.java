package com.yd1994.alpacablog.blogservice.config.cache;

import com.yd1994.alpacablog.blogservice.config.cache.keygenerator.CustomKeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存配置
 *
 * @author yd
 */
@EnableCaching
@Configuration
public class CacheConfigurer {

    @Bean
    public CustomKeyGenerator CustomKeyGenerator() {
        return new CustomKeyGenerator();
    }

}
