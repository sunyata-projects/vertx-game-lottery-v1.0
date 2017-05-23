package org.sunyata.octopus;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by aleksandr on 01.09.15.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SimpleClientApplication {

    public static void main(String[] args) {
        //SpringApplication.run(SimpleClientApplication.class, args);
        new SpringApplicationBuilder(SimpleClientApplication.class).web(true).run(args);
    }

//    @ThriftClient(serviceId = "world-service", path = "/api")
//    TGreetingService.Client client;
}
