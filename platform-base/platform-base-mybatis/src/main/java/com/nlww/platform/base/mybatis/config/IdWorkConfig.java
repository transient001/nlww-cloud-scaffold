package com.nlww.platform.base.mybatis.config;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class IdWorkConfig {
    private final RedisTemplate redisTemplate;
    private final String applicationName;

    public IdWorkConfig(RedisTemplate redisTemplate,
                        @Value("${spring.application.name}") String applicationName) {
        this.redisTemplate = redisTemplate;
        this.applicationName = applicationName;
    }

    /**
     * 自定义workerId，保证该应用的ID不会重复
     *
     * @return 新的id生成器
     */
    @Primary
    @Bean
    public DefaultIdentifierGenerator defaultIdentifierGenerator() {
        String MAX_ID = applicationName + "-worker-id";
        Long maxId = this.getWorkerId(MAX_ID);
        String maxIdStr = Long.toBinaryString(maxId);
        // 将数据补全为10位
        maxIdStr = StringUtils.leftPad(maxIdStr, 10, "0");

        // 从中间进行拆分
        String datacenterStr = maxIdStr.substring(0, 5);
        String workerStr = maxIdStr.substring(5, 10);

        // 将拆分后的数据转换成dataCenterId和workerId
        long dataCenterId = Integer.parseInt(datacenterStr, 2);
        long workerId = Integer.parseInt(workerStr, 2);
        return new DefaultIdentifierGenerator(workerId, dataCenterId);
    }

    /**
     * LUA脚本获取workerId，保证每个节点获取的workerId都不相同
     *
     * @param key 当前微服务的名称
     * @return workerId
     */
    private Long getWorkerId(String key) {
        String luaStr = "local isExist = redis.call('exists', KEYS[1])\n" +
                "if isExist == 1 then\n" +
                "    local workerId = redis.call('get', KEYS[1])\n" +
                "    workerId = (workerId + 1) % 1024\n" +
                "    redis.call('set', KEYS[1], workerId)\n" +
                "    return workerId\n" +
                "else\n" +
                "    redis.call('set', KEYS[1], 0)\n" +
                "    return 0\n" +
                "end";
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        // 以下两种二选一即可
        redisScript.setScriptText(luaStr);
        redisScript.setResultType(Long.class);
        return (Long) redisTemplate.execute(redisScript, Collections.singletonList(key));
    }
}
