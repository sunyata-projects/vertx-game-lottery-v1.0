package com.xt.landlords.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by leo on 17/5/19.
 */
@Component
public class MyRunner implements CommandLineRunner {
    @Autowired
    PuzzleCardServiceHandler puzzleCardServiceHandler;

    @Override
    public void run(String... args) throws Exception {
        puzzleCardServiceHandler.initialize();
        puzzleCardServiceHandler.getCards(3);
        puzzleCardServiceHandler.getCards(3);
        puzzleCardServiceHandler.getCards(3);
        puzzleCardServiceHandler.getCards(3);
    }
}
