package com.yd1994.alpacablog.blogservice.config.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * redis 缓存配置
 *
 * @author yd
 */
@Configuration
public class RedisCacheConfigurer extends CachingConfigurerSupport {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        // entryTtl 中 return redisCacheConfiguration。
        RedisCacheConfiguration redisCacheConfigurationEntity = redisCacheConfiguration.entryTtl(Duration.ofDays(30)).disableCachingNullValues();
        RedisCacheConfiguration sysConfiguration = redisCacheConfiguration.entryTtl(Duration.ofSeconds(300)).disableCachingNullValues();

        Set<String> cacheNames = new HashSet<>();
        cacheNames.add("articles");
        cacheNames.add("categories");
        cacheNames.add("articleTag");
        cacheNames.add("sys_information");
        cacheNames.add("file");
        cacheNames.add("users");

        Map<String, RedisCacheConfiguration> cacheConfigurationMap = new HashMap<>();
        cacheConfigurationMap.put("articles", redisCacheConfigurationEntity);
        cacheConfigurationMap.put("categories", redisCacheConfigurationEntity);
        cacheConfigurationMap.put("articleTag", redisCacheConfigurationEntity);
        cacheConfigurationMap.put("sys_information", redisCacheConfigurationEntity);
        cacheConfigurationMap.put("file", redisCacheConfigurationEntity);
        cacheConfigurationMap.put("users", sysConfiguration);

        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
                .initialCacheNames(cacheNames)
                .withInitialCacheConfigurations(cacheConfigurationMap)
                .build();

        return cacheManager;
    }

}
