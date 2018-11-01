package com.xt.card.model;

/**
 * Created by leo on 17/6/2.
 */
public class PuzzleCard {
    private String id;
    private String cards;
    private int prize_level;

    public String getId() {
        return id;
    }

    public PuzzleCard setId(String id) {
        this.id = id;
        return this;
    }

    public String getCards() {
        return cards;
    }

    public PuzzleCard setCards(String cards) {
        this.cards = cards;
        return this;
    }

    public int getPrize_level() {
        return prize_level;
    }

    public PuzzleCard setPrize_level(int prize_level) {
        this.prize_level = prize_level;
        return this;
    }
}
