package com.xt.yde.custom;

import java.util.List;

/**
 * Created by leo on 17/8/29.
 */
public class CardConfigInfo {
    private Integer gameType;
    private Integer prizeRandom;
    private List<String> cardIds;

    public Integer getGameType() {
        return gameType;
    }

    public CardConfigInfo setGameType(Integer gameType) {
        this.gameType = gameType;
        return this;
    }


    public List<String> getCardIds() {
        return cardIds;
    }

    public CardConfigInfo setCardIds(List<String> cardIds) {
        this.cardIds = cardIds;
        return this;
    }


    public Integer getPrizeRandom() {
        return prizeRandom;
    }

    public CardConfigInfo setPrizeRandom(Integer prizeRandom) {
        this.prizeRandom = prizeRandom;
        return this;
    }


}
