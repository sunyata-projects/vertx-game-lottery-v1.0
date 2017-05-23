package com.xt.yde.job;

import org.springframework.context.annotation.Configuration;

/**
 * Created by leo on 17/5/16.
 */
@Configuration
public class JobServerConfiguration{
//    @Bean
//    public OctopusConfiguration configuration(StoreFactory storeFactory) {
//        OctopusConfiguration configuration = new OctopusConfiguration();
//        configuration.setPort(8000);
//        configuration.setStoreFactory(storeFactory);
//        configuration.setMethodHandlerLocator(SpringServiceLocator.class);
//        return configuration;
//    }

//    @Value("${yde.redis.host}")
//    private String redisHost;
//
//    @Value("${yde.redis.port:6379}")
//    private Integer redisPort;

//    @Bean
//    public StoreFactory storeFactory(RedissonClient client) {
//        return new RedissonStoreFactory(client);
//    }
//
//
//    @Bean
//    public RedissonClient redissonClient() {
//        Config config = new Config();
//        config.useSingleServer().setAddress(String.format("%s:%s", redisHost, redisPort));
//        RedissonClient redissonClient = Redisson.create(config);
//        return redissonClient;
//    }
}

