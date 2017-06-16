package org.sunyata.octopus;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.sunyata.octopus.thrift.TGreetingService;
import org.sunyata.octopus.thrift.TName;
import org.sunyata.spring.thrift.client.annotation.ThriftClient;

/**
 * Created by leo on 17/4/18.
 */
@Component
public class TestCommandRunner implements CommandLineRunner {
    @ThriftClient(serviceId = "world-service", path = "/api")
    TGreetingService.Client client;

    @Override
    public void run(String... args) throws Exception {
        String greet = client.greet(new TName("firstName", "secondName"));
        for (int i = 0; i < 100; i++) {
            System.out.println(greet);
        }
    }
}
