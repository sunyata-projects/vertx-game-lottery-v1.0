package org.sunyata.spring.boot.thrift.client;

/**
 * Created by leo on 17/4/18.
 */

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.sunyata.spring.thrift.client.annotation.EnableThriftClient;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableThriftClient
public class Application {


    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext run = new SpringApplicationBuilder(Application.class).run(args);
    }

    @PostConstruct
    public void initCoordination() {

    }

}
