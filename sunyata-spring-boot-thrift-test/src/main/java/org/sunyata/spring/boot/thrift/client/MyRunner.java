//package org.sunyata.spring.boot.thrift.client;
//
//import com.xt.yde.thrift.card.eliminate.EliminateCards;
//import com.xt.yde.thrift.card.eliminate.EliminateCardsService;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import org.sunyata.spring.thrift.client.annotation.ThriftClient;
//
///**
// * Created by leo on 17/6/14.
// */
//@Component
//public class MyRunner implements CommandLineRunner {
//    @ThriftClient(serviceId = "yde-card-service", path = "/eliminate")
//    EliminateCardsService.Client cardService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        EliminateCards cards = cardService.getCards(1, 1);
//        System.out.println("cards:");
//    }
//}
