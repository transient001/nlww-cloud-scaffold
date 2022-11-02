package com.nlww.platform.base.redisson.configure;

//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.data.redis.core.RedisTemplate;

/**
 * RedissonConfig硬编码方式
 *
 * @author 渔火江渚上
 * @since 2022/7/17 23:03
 */
//@Configuration
public class RedissonConfig {
//    @Value("${spring.redis.urls}")
//    private String urls;
//    @Value("${spring.redis.password}")
//    private String password;
//
//    @Profile("dev")
//    @Bean(name = "redissonClient")
//    public RedissonClient redissonSingle() {
//        Config config = new Config();
//        //配置地址、数据库
//        config.useSingleServer()
//                .setAddress("redis://localhost:6379")
//                .setPassword(password)
//                .setDatabase(0);
//        return Redisson.create(config);
//    }
//
//    @Profile("prod")
//    @Bean(name = "redissonClient")
//    public RedissonClient redisson() {
//        Config config = new Config();
//        //配置地址、数据库
//        config.useClusterServers() // 这是用的集群server
//                .setScanInterval(2000) // 集群状态扫描间隔时间，单位是毫秒
//                .addNodeAddress(nodes)
//                .setPassword(password);
//
//        return Redisson.create(config);
//    }
}
