package com.xt.landlords;

/**
 * Created by leo on 17/4/21.
 */
public class GameCycle {
    private GamePhase gamePhase;

    public void initBet() {

    }

    public void AdditionBet() {
        gamePhase = new AdditionBetGamePhase();
        gamePhase.run(this);
    }

    public void run() {
        gamePhase = new InitBetGamePhase();
        gamePhase.run(this);
    }
}

class InitBetGamePhase implements GamePhase {


    @Override
    public void run(GameCycle gameCycle) {

    }
}

class AdditionBetGamePhase implements GamePhase {


    @Override
    public void run(GameCycle gameCycle) {

    }
}

interface GamePhase {
    void run(GameCycle gameCycle);
}

