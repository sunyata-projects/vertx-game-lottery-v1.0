package com.xt.landlords.card;

/**
 * Created by leo on 17/4/18.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableScheduling
public class Application {

//    @Autowired
//    BusinessManager businessManager;

    public static void main(String[] args) throws InterruptedException {
        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }

    //
//    @Bean
//    public AlwaysSampler defaultSampler() {
//        return new AlwaysSampler();
//    }
    @Autowired
    AppDBCards appDBCards;
    @Autowired
    EliminateCardServiceHandler eliminateCardServiceHandler;

    @Autowired
    RegularCardServiceHandler regularCardServiceHandler;

    @Autowired
    PuzzleCardServiceHandler puzzleCardServiceHandler;

    @PostConstruct
    public void initCoordination() throws IOException {
        puzzleCardServiceHandler.initialize();
        eliminateCardServiceHandler.initialize();
        regularCardServiceHandler.initialize();
        //appDBCards.initialize();
    }

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
