//package com.xt.landlords.card;
//
//import com.xt.yde.thrift.card.eliminate.EliminateCards;
//import com.xt.yde.thrift.regular.RegularCards;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
///**
// * Created by leo on 17/5/19.
// */
//@Component
//public class MyRunner implements CommandLineRunner {
//    @Autowired
//    PuzzleCardServiceHandler puzzleCardServiceHandler;
//
//    @Autowired
//    EliminateCardServiceHandler eliminateCardServiceHandler;
//
//    @Autowired
//    RegularCardServiceHandler regularCardServiceHandler;
//
//    @Override
//    public void run(String... args) throws Exception {
//        puzzleCardServiceHandler.initialize();
//        eliminateCardServiceHandler.initialize();
//        EliminateCards cards = eliminateCardServiceHandler.getCards(1, 3);
//        RegularCards cards17 = regularCardServiceHandler.getCards17();
//        RegularCards cards37 = regularCardServiceHandler.getCards37(3, cards17.getCardId());
////        puzzleCardServiceHandler.getCards(3);
////        puzzleCardServiceHandler.getCards(3);
////        puzzleCardServiceHandler.getCards(3);
////        puzzleCardServiceHandler.getCards(3);
//    }
//}
