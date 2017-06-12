package com.xt.landlords;

import com.xt.landlords.ioc.SpringServiceLocator;
import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger = LoggerFactory.getLogger(NettyServerConfiguration.class);

    @Bean
    public OctopusConfiguration configuration(StoreFactory storeFactory) {
        OctopusConfiguration configuration = new OctopusConfiguration();
        configuration.setPort(8000);
        configuration.setStoreFactory(storeFactory);
        configuration.setMethodHandlerLocator(SpringServiceLocator.class);
        return configuration;
    }

    @Value("${yde.redis.url}")
    private String redisUrl;
//
//    @Value("${yde.redis.port:6379}")
//    private Integer redisPort;

    @Value("${yde.redis.password}")
    private String redisPassword;

    @Bean
    public StoreFactory storeFactory(RedissonClient client) {
        return new RedissonStoreFactory(client);
    }


    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        String[] urls = redisUrl.split(",");
        logger.info("redis密码为:{}", redisPassword);
        if (urls.length == 1) {
            SingleServerConfig singleServerConfig = config.useSingleServer().setAddress(String.format("%s", redisUrl));
            if (!StringUtils.isEmpty(redisPassword)) {
                singleServerConfig.setPassword(redisPassword);
            }
        } else {
            ClusterServersConfig clusterServersConfig = config.useClusterServers().addNodeAddress(urls[0], urls[1]);
            if (!StringUtils.isEmpty(redisPassword)) {
                clusterServersConfig.setPassword(redisPassword);
            }

        }
        return Redisson.create(config);
    }

    @Bean()
    QuarkClient quarkClient() {
        return new QuarkClientImpl(null, "", "", "yde-quark-service");
    }
}
