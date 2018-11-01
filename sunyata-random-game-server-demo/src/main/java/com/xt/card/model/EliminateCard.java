package com.xt.card.model;

/**
 * Created by leo on 17/6/2.
 */
public class EliminateCard {
    private String id;
    private String cards;
    private int prize_level;
    private int bomb_numbers;

    public String getId() {
        return id;
    }

    public EliminateCard setId(String id) {
        this.id = id;
        return this;
    }

    public String getCards() {
        return cards;
    }

    public EliminateCard setCards(String cards) {
        this.cards = cards;
        return this;
    }

    public int getPrize_level() {
        return prize_level;
    }

    public EliminateCard setPrize_level(int prize_level) {
        this.prize_level = prize_level;
        return this;
    }

    public int getBomb_numbers() {
        return bomb_numbers;
    }

    public EliminateCard setBomb_numbers(int bomb_numbers) {
        this.bomb_numbers = bomb_numbers;
        return this;
    }
}
