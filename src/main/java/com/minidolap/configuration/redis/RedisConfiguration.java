package com.minidolap.configuration.redis;

import com.minidolap.persistence.entity.login.ApplicationUser;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

/*
@Data
@Configuration
@ConfigurationProperties(prefix = "redis")
public class RedisConfiguration {
    private String hostName;
    private int port;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(hostName);
        jedisConnectionFactory.setPort(port);
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, ApplicationUser> userRedisTemplate() {
        final RedisTemplate<String, ApplicationUser> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}
*/