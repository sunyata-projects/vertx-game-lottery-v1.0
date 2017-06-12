package com.zhongqi;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoverServerApplication {

    public static void main(String[] args) {
        //SpringApplication.run(DiscoverServerApplication.class, args);
        new SpringApplicationBuilder(DiscoverServerApplication.class).web(true).run(args);
    }
}
