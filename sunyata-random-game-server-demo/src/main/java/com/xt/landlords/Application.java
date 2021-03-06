package com.xt.landlords;

/**
 * Created by leo on 17/4/18.
 */

import com.xt.card.AppDBCards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.sunyata.spring.thrift.client.annotation.EnableThriftClient;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableScheduling
//@EnableQuarkClient
@EnableThriftClient
@ComponentScan("com.xt.card")
@ComponentScan("com.xt.ai")
@ComponentScan("com.xt.yde.custom")
public class Application {

//    @Autowired
//    BusinessManager businessManager;

    public static void main(String[] args) throws InterruptedException {
        new SpringApplicationBuilder(Application.class).web(false).run(args);
    }
    @Autowired
    AppDBCards appDBCards;
//    @Autowired
//    EliminateCardServiceHandler eliminateCardServiceHandler;
//
//    @Autowired
//    RegularCardServiceHandler regularCardServiceHandler;
//
//    @Autowired
//    PuzzleCardServiceHandler puzzleCardServiceHandler;

    @PostConstruct
    public void initCoordination() throws IOException {
//        puzzleCardServiceHandler.initialize();
//        eliminateCardServiceHandler.initialize();
//        regularCardServiceHandler.initialize();
        //appDBCards.initialize();
    }
    //
//    @Bean
//    public AlwaysSampler defaultSampler() {
//        return new AlwaysSampler();
//    }
//    @PostConstruct
//    public void initCoordination() {
//
//    }

//    @Bean
//    BusinessManager businessManager() throws Exception {
//        BusinessManager c = new MultipleThreadBusinessManager();
//        c.setScanPackage("org.sunyata.quark.embed.demo.springcloud.components");
//        c.setServiceLocator(SpringServiceLocator.class);
//        c.setEventPublisher(SpringEventEventPublisher.class);
//        c.initialize();
////        c.register(ParallelBusinessComponent.class);
////        c.register(SingleBusinessComponent.class);
////        c.register(TwoBusinessComponent.class);
//        return c;
//    }
}
