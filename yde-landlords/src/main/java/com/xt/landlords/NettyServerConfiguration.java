package com.xt.landlords;

import com.xt.landlords.ioc.SpringServiceLocator;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sunyata.octopus.OctopusConfiguration;
import org.sunyata.octopus.store.RedissonStoreFactory;
import org.sunyata.octopus.store.StoreFactory;
import org.sunyata.quark.client.QuarkClient;
import org.sunyata.quark.client.QuarkClientImpl;

/**
 * Created by leo on 17/4/19.
 */
@Configuration
public class NettyServerConfiguration {
    @Bean
    public OctopusConfiguration configuration(StoreFactory storeFactory) {
        OctopusConfiguration configuration = new OctopusConfiguration();
        configuration.setPort(8000);
        configuration.setStoreFactory(storeFactory);
        configuration.setMethodHandlerLocator(SpringServiceLocator.class);
        return configuration;
    }

    @Value("${yde.redis.host}")
    private String redisHost;

    @Value("${yde.redis.port:6379}")
    private Integer redisPort;

    @Bean
    public StoreFactory storeFactory(RedissonClient client) {
        return new RedissonStoreFactory(client);
    }


    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress(String.format("%s:%s", redisHost, redisPort));
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }

    @Bean()
    QuarkClient quarkClient() {
        return new QuarkClientImpl(null, "", "", "yde-quark-service");
    }
}
